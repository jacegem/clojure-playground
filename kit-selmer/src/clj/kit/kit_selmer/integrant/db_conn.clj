(ns kit.kit-selmer.integrant.db-conn
  (:require [integrant.core :as ig]
            [hikari-cp.core :refer [close-datasource make-datasource]]))

(defmethod ig/init-key :db.conn/local.info [_ config]
  (make-datasource config))

(defmethod ig/halt-key! :db.conn/local.info [_ conn]
  (close-datasource conn))