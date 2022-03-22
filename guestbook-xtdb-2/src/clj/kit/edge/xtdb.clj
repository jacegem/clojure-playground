


(ns kit.edge.xtdb
  (:require [integrant.core :as ig]
            [xtdb.api :as xtdb]
            [clojure.pprint :as pprint]))


(defmethod ig/init-key :xtdb.jdbc/connection-pool
  [_ config]
  config)


(defmethod ig/init-key :db.xtdb/node
  [_ config]
  (prn config)
  ;; (xtdb/start-node config)

  ;; (let [c config]
  ;;   (prn c)
  ;;   (xtdb/start-node c))
  (let [config-1  {:xtdb.jdbc/connection-pool {:dialect {:xtdb/module 'xtdb.jdbc.mysql/->dialect},
                                               :db-spec {:host "localhost", :dbname "xtdb", :user "root", :password "my-password"}},
                   :xtdb/tx-log {:xtdb/module 'xtdb.jdbc/->tx-log, :connection-pool :xtdb.jdbc/connection-pool},
                   :xtdb/document-store {:xtdb/module 'xtdb.jdbc/->document-store, :connection-pool :xtdb.jdbc/connection-pool}}]
    (prn (= config config-1))
    (xtdb/start-node config))

  #_(xtdb/start-node {:xtdb.jdbc/connection-pool {:dialect {:xtdb/module 'xtdb.jdbc.mysql/->dialect},
                                                  :db-spec {:host "localhost", :dbname "xtdb", :user "root", :password "my-password"}},
                      :xtdb/tx-log {:xtdb/module 'xtdb.jdbc/->tx-log, :connection-pool :xtdb.jdbc/connection-pool},
                      :xtdb/document-store {:xtdb/module 'xtdb.jdbc/->document-store, :connection-pool :xtdb.jdbc/connection-pool}})
  #_(xtdb/start-node {:xtdb.jdbc/connection-pool {:dialect {:xtdb/module 'xtdb.jdbc.mysql/->dialect},
                                                  :db-spec {:host "localhost", :dbname "xtdb", :user "root", :password "my-password"}},
                      :xtdb/tx-log {:xtdb/module 'xtdb.jdbc/->tx-log,
                                    :connection-pool :xtdb.jdbc/connection-pool},
                      :xtdb/document-store {:xtdb/module 'xtdb.jdbc/->document-store,
                                            :connection-pool :xtdb.jdbc/connection-pool}}))


(def a {:xtdb.jdbc/connection-pool {:dialect {:xtdb/module 'xtdb.jdbc.mysql/->dialect}
                                    :db-spec {:host "localhost"
                                              :dbname "xtdb"
                                              :user "root"
                                              :password "my-password"}}
        :xtdb/tx-log {:xtdb/module 'xtdb.jdbc/->tx-log
                      :connection-pool :xtdb.jdbc/connection-pool}
        :xtdb/document-store {:xtdb/module 'xtdb.jdbc/->document-store
                              :connection-pool :xtdb.jdbc/connection-pool}})

(def b {:xtdb.jdbc/connection-pool {:dialect {:xtdb/module 'xtdb.jdbc.mysql/->dialect,
                                              :prevent true},
                                    :db-spec {:host "localhost", :dbname "xtdb", :user "root", :password "my-password"}},
        :xtdb/tx-log {:xtdb/module 'xtdb.jdbc/->tx-log,
                      :connection-pool :xtdb.jdbc/connection-pool},
        :xtdb/document-store {:xtdb/module 'xtdb.jdbc/->document-store,
                              :connection-pool :xtdb.jdbc/connection-pool}})

(= a b)
(into {} b)

(defmethod ig/halt-key! :db.xtdb/node
  [_ xtdb-node]
  (.close xtdb-node))