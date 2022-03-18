(ns web.core
  (:gen-class)
  (:require [clojure.java.io :as io]
            [hikari-cp.core :refer [close-datasource make-datasource]]
            [integrant.core :as ig]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.reload :refer [wrap-reload]]
            [web.route :as route]
            [integrant.repl :refer [set-prep! go halt reset reset-all]]))

(defmethod ig/init-key :db/xtdb [_ config] (make-datasource config))
(defmethod ig/halt-key! :db/xtdb [_ conn] (close-datasource conn))

(defmethod ig/init-key :db/user [_ config] (make-datasource config))
(defmethod ig/halt-key! :db/user [_ conn] (close-datasource conn))

(defmethod ig/init-key :app/config [_ config] config)
(defmethod ig/init-key :handler/app [_ config] (route/app config))

(defmethod ig/init-key :server/jetty
  [_ {:keys [handler port]}]
  (run-jetty handler {:port port :join? false}))

(defmethod ig/halt-key! :server/jetty
  [_ server]
  (.stop server))

(defn -main
  []
  (-> "config.edn"
      io/resource
      slurp
      ig/read-string
      ig/prep
      ig/init))

(comment
  (reset)
  '())



;; (defn ig-db [& keys]
;;   (map (fn [key]
;;          (defmethod ig/init-key key [_ config] (make-datasource config))
;;          (defmethod ig/halt-key! key [_ conn] (close-datasource conn)))
;;        keys))

;; (ig-db :db/xtdb :db/user)

;; (defmethod ig/init-key :server/jetty
;;   [_ {:keys [handler port]}]
;;   (run-jetty (wrap-reload handler) {:port port :join? false}))

;; (defmethod ig/init-key :db/xtdb
;;   [_ config]
;;   (make-datasource config))

;; (defmethod ig/init-key :db/user
;;   [_ config]
;;   (make-datasource config))

;; (defmethod ig/suspend-key! :server/jetty
;;   [_ server]
;;   (.stop server))

;; (defmethod ig/halt-key! :db/xtdb
;;   [_ conn]
;;   (close-datasource conn))

;; (defmethod ig/halt-key! :db/user
;;   [_ conn]
;;   (close-datasource conn))

;; (defmethod ig/suspend-key! :db/xtdb
;;   [_ conn]
;;   (close-datasource conn))

;; (defmethod ig/suspend-key! :db/user
;;   [_ conn]
;;   (close-datasource conn))