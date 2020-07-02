(ns app.core
  (:require [org.httpkit.server :refer [run-server]]
            [environ.core :refer [env]]
            [app.handler :refer [app-handler]]))

(defn -main [& args]
  (let [port (Integer. (or (env :port) 3000))]
    (run-server app-handler {:port port})
    (prn (str "Server started on port: " port))))

