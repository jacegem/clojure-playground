(ns hello
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]))

(defn respond-hello [request]
  {:status 200 :body "Hello, world! 2"})

(defn respond-hello-2 [request]
  (let [nm (get-in request [:query-params :name])]
    {:status 200 :body (str "Hello, " nm "\n")}))

(defn echo [request]
  {:status 200 :body "Hello, world! 2"})

(def routes
  (route/expand-routes
   #{["/greet" :get respond-hello-2 :route-name :greet]
     ["/echo"  :get echo]}))

(def service-map
  {::http/routes routes
   ::http/type   :jetty
   ::http/port   8890})

(defn start []
  (http/start (http/create-server service-map)))

(defonce server (atom nil))

(defn start-dev []
  (reset! server
          (http/start (http/create-server
                       (assoc service-map
                              ::http/join? false)))))

(defn stop-dev []
  (http/stop @server))

(defn restart []
  (stop-dev)
  (start-dev))

(comment
  (start-dev)
  (stop-dev)
  (restart)
  '())