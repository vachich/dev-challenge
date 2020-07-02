(ns app.integration-test
  (:require [clojure.test :refer :all]
            [expectations.clojure.test :refer :all]
            [app.handler :refer [app-handler]]
            [org.httpkit.server :refer [run-server]]
            [clj-http.client :refer [post]])
  (:import [clojure.lang ExceptionInfo]))
(defn with-test-server [t]
  (let [server (atom nil)]
    (reset! server (run-server #'app-handler {:port 3001}))
    (t)
    (@server)))

(use-fixtures :once with-test-server)
(def url "http://localhost:3001")

(deftest integration
  (expecting "200 status code and json body with correct result"
    (let [response (post url {:content-type :json
                              :body         "{\n\"address\": {\n\"colorKeys\": [\n\"A\",\n\"G\",\n\"Z\"\n],\n\"values\": [\n74,\n117,\n115,\n116,\n79,\n110\n]\n},\n\"meta\": {\n\"digits\": 33,\n\"processingPattern\": \"d{5}+[a-z&$§]\"\n}\n}"})]

      (expect "{\"result\":8}" (:body response))
      (expect 200 (:status response))))

(expecting "422 Status code and Invalid input error message when all values are not positive int or zero"
  (let [response (try (post url {:content-type :json
                                 :body         "{\n\"address\": {\n\"colorKeys\": [\n\"A\",\n\"G\",\n\"Z\"\n],\n\"values\": [\n74,\n117,\n-115,\n116,\n79,\n110\n]\n},\n\"meta\": {\n\"digits\": 33,\n\"processingPattern\": \"d{5}+[a-z&$§]\"\n}\n}"})
                      (catch ExceptionInfo e
                        (ex-data e)))]
    (expect 422 (:status response))
    (expect "{\"error\":\"Invalid input, expected values to be positive int or zero.\"}" (:body response))))

  (expecting "422 Status code and error json message when missing values"
    (let [response (try (post url {:content-type :json
                                   :body         "{\n\"address\": {\n\"colorKeys\": [\n\"A\",\n\"G\",\n\"Z\"\n]},\n\"meta\": {\n\"digits\": 33,\n\"processingPattern\": \"d{5}+[a-z&$§]\"\n}\n}"})
                        (catch ExceptionInfo e
                          (ex-data e)))]
      (expect 422 (:status response))
      (expect "{\"error\":\"Invalid input\"}" (:body response)))))

