(ns app.handler
  (:require
    [compojure.core :refer :all]
    [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
    [app.service :as service]
    [app.middleware :refer [wrap-exceptions wrap-content-type-json]]))

(defroutes app-routes
  (POST "/" [] (fn [req] {:status 200 :body (service/compute (-> req :body))}))
  (ANY "*" [] (fn [_] {:status 404 :body {:error "not found"}})))

(def app-handler
  (-> app-routes
    wrap-content-type-json
    wrap-exceptions
    wrap-json-response
    (wrap-json-body {:keywords? true})))
