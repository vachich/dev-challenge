(ns app.middleware)

(defn wrap-exceptions [handler]
  "Middleware that uses the :code and message of the exception to create a response map, it fallbacks to a generic 500 Server Error if :code is nil.

  Eg: (wrap-exceptions (fn[request] (throw (ex-info 'Invalid input' {:code 400}))) => {:status 400 :body {:error 'Invalid input'}}"
  (fn [request]
    (try
      (handler request)
      (catch Throwable e
        (if (:code (ex-data e))
          {:status (ex-data e) :body {:error (ex-message e)} :headers {"Content-Type" "application/json"}}
          {:status 500 :body {:error "Server Error"} :headers {"Content-Type" "application/json"}})))))