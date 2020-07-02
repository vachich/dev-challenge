(defproject app "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [compojure "1.6.1"]
                 [http-kit "2.3.0"]
                 [ring/ring-core "1.8.1"]
                 [ring/ring-json "0.5.0"]
                 [environ "1.2.0"]
                 [ring/ring-defaults "0.3.2"]]
  :plugins []
  :main app.core
  :profiles
  {:dev {:dependencies [[expectations/clojure-test "1.2.1"]
                        [clj-http "3.10.1"]]}})
