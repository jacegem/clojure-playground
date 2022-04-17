(ns kit.guestbook-xtdb-2.core
  (:require
   [clojure.tools.logging :as log]
   [integrant.core :as ig]
   [kit.guestbook-xtdb-2.config :as config]
   [kit.guestbook-xtdb-2.env :refer [defaults]]

    ;; Edges  
   [kit.edge.db.xtdb]
   [kit.edge.server.undertow]
   [kit.guestbook-xtdb-2.web.handler]

    ;; Routes
   [kit.guestbook-xtdb-2.web.routes.api])
  (:gen-class))



;; log uncaught exceptions in threads
(Thread/setDefaultUncaughtExceptionHandler
 (reify Thread$UncaughtExceptionHandler
   (uncaughtException [_ thread ex]
     (log/error {:what :uncaught-exception
                 :exception ex
                 :where (str "Uncaught exception on" (.getName thread))}))))

(defonce system (atom nil))

(defn stop-app []
  ((or (:stop defaults) (fn [])))
  (some-> (deref system) (ig/halt!))
  (shutdown-agents))

(defn start-app [& [params]]
  ((or (:start params) (:start defaults) (fn [])))
  (->> (config/system-config (or (:opts params) (:opts defaults) {}))
       (ig/prep)
       (ig/init)
       (reset! system))
  (.addShutdownHook (Runtime/getRuntime) (Thread. stop-app)))

(defn -main [& _]
  (start-app))
