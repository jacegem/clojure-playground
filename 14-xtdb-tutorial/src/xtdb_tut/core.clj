(ns xtdb-tut.core
  (:require  [xtdb.api :as xt]))

(def node
  (xt/start-node
   {:xtdb.jdbc/connection-pool {:dialect {:xtdb/module 'xtdb.jdbc.mysql/->dialect}
                                :db-spec {:host "localhost"
                                          :dbname "xtdb"
                                          :user "root"
                                          :password "my-password"}}
    :xtdb/tx-log {:xtdb/module 'xtdb.jdbc/->tx-log
                  :connection-pool :xtdb.jdbc/connection-pool}
    :xtdb/document-store {:xtdb/module 'xtdb.jdbc/->document-store
                          :connection-pool :xtdb.jdbc/connection-pool}}))

(defn prepare-docs [docs]
  (mapv (fn [doc]
          [::xt/put doc #inst "2021-01-04T18:00:00.000-00:00"]) docs))



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



(with-open [res (xt/open-q (xt/db node2)
                           '{:find [p1]
                             :where [[p1 :name n]
                                     [p1 :last-name n]
                                     [p1 :name "Smith"]]})]
  (doseq [tuple (iterator-seq res)]
    (prn tuple)))


(def products-2 [{:xt/id :towel1
                  :prod/name "Purple Towel"
                  :prod/quantity 7
                  :prod/date-updated #inst "2021-11-10T18:00:00.000-00:00"
                  :prod/updated-by "Allan"
                  :prod/price 200}

                 {:xt/id :facecloth1
                  :prod/name "Blue Facecloth"
                  :prod/quantity 1
                  :prod/date-updated #inst "2021-11-10T18:00:00.000-00:00"
                  :prod/updated-by "Allan"
                  :prod/price 50}])

(def products-3 [{:xt/id :towel1
                  :prod/name "Purple Towel"
                  :prod/quantity 17
                  :prod/date-updated #inst "2021-11-12T18:00:00.000-00:00"
                  :prod/updated-by "Dan"
                  :prod/price 210}

                 {:xt/id :facecloth1
                  :prod/name "Blue Facecloth"
                  :prod/quantity 6
                  :prod/date-updated #inst "2021-11-12T18:00:00.000-00:00"
                  :prod/updated-by "Allan"
                  :prod/price 55}])

(def products-4 [{:xt/id :towel1
                  :prod/name "Purple Towel"
                  :prod/quantity 20
                  :prod/date-updated #inst "2021-11-12T18:00:00.000-00:00"
                  :prod/updated-by "Dan"
                  :prod/price 210}

                 {:xt/id :facecloth1
                  :prod/name "Blue Facecloth"
                  :prod/quantity 10
                  :prod/date-updated #inst "2021-11-12T18:00:00.000-00:00"
                  :prod/updated-by "Dan"
                  :prod/price 55}])



(comment
  (xt/submit-tx node
                (prepare-docs products-1))
  nil)

(def history
  (xt/entity-history
   (xt/db node)
   :towel1 :desc {:with-docs? true}))
history

(->> (map ::xt/doc history)
     (filter #(= (get % :prod/updated-by) "Dan")))

(xt/q (xt/db node)
      '{:find [?e]
        :where [[?e :xt/id :towel1]]})

(xt/q (xt/db node)
      '{:find [(pull ?e [*])]
        :where [[?e :xt/id :towel1]]})


(xt/q (xt/db node {::xt/valid-time #inst "2021-01-01T18:00:00.000-00:00"})
      '{:find [(pull ?e [*])]
        :where [[?e :xt/id :towel1]]})

(xt/q (xt/db node {::xt/valid-time #inst "2021-01-01T18:00:00.000-00:00"})
      '{:find [(pull ?e [*])]
        :where [[?e :xt/id]]})

(xt/q (xt/db node {::xt/valid-time #inst "2021-01-01T18:00:00.000-00:00"})
      '{:find [(pull ?e [*]) ?total]
        :where [[?e :xt/id]
                [?e :prod/price ?price]
                [?e :prod/quantity ?quantity]
                [(* ?price ?quantity) ?total]]})

(xt/q (xt/db node {::xt/valid-time #inst "2021-01-03T18:00:00.000-00:00"})
      '{:find [(sum ?total)]
        :where [[?e :xt/id]
                [?e :prod/price ?price]
                [?e :prod/quantity ?quantity]
                [(* ?price ?quantity) ?total]]})


(xt/q (xt/db node {::xt/valid-time #inst "2021-01-03T18:00:00.000-00:00"})
      '{:find [(pull ?e [*])]
        :where [[?e :xt/id]
                [?e :prod/price ?price]
                [?e :prod/quantity ?quantity]
                [(* ?price ?quantity) ?total]]})









