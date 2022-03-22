(ns core
  (:require [integrant.core :as ig]
            [clojure.pprint :refer [pprint]]
            ;; [xtdb.jdbc :as jdbc]
            [xtdb.api :as xt]
            [clojure.java.io :as io]))

(defmethod ig/init-key :db.xtdb/node
  [_ config]
  (pprint config)
  (:require '[xtdb.api :as xt])
  (xt/start-node config))

(defmethod ig/halt-key! :db.xtdb/node
  [_ xtdb-node]
  (.close xtdb-node))


(def node2
  (xt/start-node {:xtdb.jdbc/connection-pool {:dialect #:xtdb{:module 'xtdb.jdbc.mysql/->dialect},
                                              :db-spec
                                              {:host "localhost",
                                               :dbname "xtdb",
                                               :user "root",
                                               :password "my-password"}},
                  :xtdb/tx-log {:xtdb/module 'xtdb.jdbc/->tx-log,
                                :connection-pool :xtdb.jdbc/connection-pool},
                  :xtdb/document-store {:xtdb/module 'xtdb.jdbc/->document-store,
                                        :connection-pool :xtdb.jdbc/connection-pool}}))



(defn -main
  []
  (let [config (-> "config.edn"
                   io/resource
                   slurp
                   ig/read-string)]
    (-> config
        ig/prep
        ig/init)))

(-main)




(def products-1 [{:xt/id :towel1
                  :prod/name "Purple Towel"
                  :prod/quantity 10
                  :prod/date-updated #inst "2021-11-02T18:00:00.000-00:00"
                  :prod/updated-by "Allan"
                  :prod/price 200}

                 {:xt/id :facecloth1
                  :prod/name "Blue Facecloth"
                  :prod/quantity 5
                  :prod/date-updated #inst "2021-11-02T18:00:00.000-00:00"
                  :prod/updated-by "Allan"
                  :prod/price 50}])


(defn prepare-docs [docs]
  (mapv (fn [doc]
          [::xt/put doc #inst "2021-01-04T18:00:00.000-00:00"]) docs))


(comment
  (xt/submit-tx node2
                (prepare-docs products-1))
  nil)