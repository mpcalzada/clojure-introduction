(ns schemas.lesson1
  (:use [clojure.pprint])
  (:require [schema.core :as s]))

(s/set-fn-validation! true)
(defn adds-patient
  [patients patient]
  (if-let [id (:id patient)]
    (assoc patients id patient)
    (throw (ex-info "Patient has no id" {:patient patient}))))

(defn adds-appointment
  [appointments patient appointment]
  (if (contains? appointments patient)
    (update appointments patient concat appointment)
    (assoc appointments patient appointment))
  )


(s/defn prints-patient-report
  [appointments
   patient :- Long]
  (println "Patient" patient "has the following appointments: " (get appointments (:id patient))))

(defn tests-patients
  []
  (let [william {:id 15, :name "William"}
        daniela {:id 20, :name "Daniela"}
        paul {:id 25, :name "Paul"}
        patients (reduce adds-patient {} [william daniela paul])
        appointments {}
        appointments (adds-appointment appointments 15 ["01/01/2020"])
        appointments (adds-appointment appointments 20 ["02/01/2020" "02/05/2020"])
        appointments (adds-appointment appointments 15 ["03/01/2020"])]
    (pprint patients)
    (pprint appointments)
    (prints-patient-report appointments 15)
    ))

(tests-patients)


(s/defn new-patient
  [id :- Long name :- s/Str]
  {:id id :name name})

(pprint (new-patient 15 "William"))