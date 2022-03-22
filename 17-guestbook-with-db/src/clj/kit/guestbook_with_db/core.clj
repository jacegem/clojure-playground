(ns kit.guestbook-with-db.core
  (:require
   [clojure.tools.logging :as log]
   [integrant.core :as ig]
   [kit.guestbook-with-db.config :as config]
   [kit.guestbook-with-db.env :refer [defaults]]


   [xtdb.api]


    ;; Edges  
   [kit.edge.db.xtdb]
   [kit.edge.db.sql.conman]
   [kit.edge.db.sql.migratus]
   #_[kit.edge.db.postgres]
   [kit.edge.server.undertow]
   [kit.guestbook-with-db.web.handler]

  ;;  [kit.guestbook-with-db.db.xtdb]



    ;; Routes
   [kit.guestbook-with-db.web.routes.api]
   [kit.guestbook-with-db.web.xt.route])
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
