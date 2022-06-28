(ns record-protocol-multimethod.lesson3
  (:use [clojure.pprint])
  (:require [record-protocol-multimethod.logic :as r.logic]))

(defn downloads-patient
  [id]
  (println "Downloading patient" id)
  (Thread/sleep 1000)
  {:id id :downloading-at (r.logic/this-moment)})


(defn downloads-if-doesnt-exists
  [cache id downloader]
  (if (contains? cache id)
    cache
    (let [patient (downloader id)]
      (assoc cache id patient))))

(defprotocol Downloadable
  (download! [this id]))

(defrecord Cache
  [cache downloader]
  Downloadable

  (download! [_ id]
    (swap! cache downloads-if-doesnt-exists id downloader)
    (get @cache id)))

;(pprint (downloads-patient 15))

;(pprint (downloads-if-doesnt-exists {} 15 downloads-patient))
;(pprint (downloads-if-doesnt-exists {15 {:id 15 :name "Juan"}} 15 downloads-patient))

(def patients (->Cache (atom {}) downloads-patient))

(pprint patients)
(pprint (download! patients 15))
(pprint (download! patients 30))
(pprint (download! patients 15))
(pprint patients)