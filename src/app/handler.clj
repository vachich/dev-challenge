(ns app.handler
  (:require
    [compojure.core :refer :all]
    [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
    [ring.middleware.json :refer [wrap-json-body wrap-json-response]]))

(defroutes app-routes
  (ANY "*" [] (fn [_] {:status 200 :body "Hello World!" :headers {"Content-Type" "application/json "}})))

(def app-handler
  (-> app-routes
    (wrap-defaults api-defaults)
    wrap-json-response
    wrap-json-body
    ))
