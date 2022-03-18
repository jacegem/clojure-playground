(ns myname.myapp
  (:gen-class)
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.cookies :refer [wrap-cookies]]
            [clojure.pprint :refer [pprint]]
            [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [not-found]]))


(defroutes app
  (GET "/" [] "Hello World 3")
  (not-found "Page not found"))

(defn handler [request]
  (pprint request)
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World 2"})

(defn -main [& args]
  (println "Server started on port 3000")
  (run-jetty (wrap-reload #'app) {:port 3000
                                  :join? false}))

(comment
  (-main)

  '())
