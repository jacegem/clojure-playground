(ns kit.guestbook-xtdb.xt
  (:require [xtdb.api :as xtdb]))




(def node
  (xtdb/start-node {:xtdb.jdbc/connection-pool {:dialect {:xtdb/module 'xtdb.jdbc.mysql/->dialect}
                                                :db-spec {:host "localhost"
                                                          :dbname "xtdb"
                                                          :user "root"
                                                          :password "my-password"}}
                    :xtdb/tx-log {:xtdb/module 'xtdb.jdbc/->tx-log
                                  :connection-pool :xtdb.jdbc/connection-pool}
                    :xtdb/document-store {:xtdb/module 'xtdb.jdbc/->document-store
                                          :connection-pool :xtdb.jdbc/connection-pool}}))