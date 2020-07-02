(ns app.handler
  (:require
    [compojure.core :refer :all]
    [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
    [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
    [app.service :as service]))

(defroutes app-routes
  (POST "/" [] (fn [req] {:status 200 :body (service/compute (-> req :body)) :headers {"Content-Type" "application/json"}}))
  (ANY "*" [] (fn [_] {:status 404 :body {:error "not found"} :headers {"Content-Type" "application/json "}})))

(def app-handler
  (-> app-routes
    (wrap-defaults api-defaults)
    wrap-json-response
    (wrap-json-body {:keywords? true})
    ))
