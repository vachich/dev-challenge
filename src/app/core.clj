(ns app.core
  (:require [org.httpkit.server :refer [run-server]]
            [compojure.core :refer :all]
            [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [environ.core :refer [env]]))

(defroutes app-routes
  (ANY "*" [] (fn [_] {:status 200 :body "Hello World!" :headers {"Content-Type" "application/json "}})))

(def app-handler
  (-> app-routes
    (wrap-defaults api-defaults)
    wrap-json-response
    wrap-json-body
    ))

(defn -main [& args]
  (let [port (Integer. (or (env :port) 3000))]
    (run-server app-handler {:port port})
    (prn (str "Server started on port: " port))))

