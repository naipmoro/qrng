(defproject naipmoro/qrng "0.5.0"
  :description "A clojure interface to the ANU Quantum Random Numbers Server"
  :url "https://github.com/naipmoro/qrng"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo} 
  :scm {:name "git"
        :url "https://github.com/naipmoro/qrng"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-http "0.6.5"]]
  :profiles {:dev {:dependencies [[criterium "0.3.1"]
                                  [midje "1.5.0"]]}}
  :min-lein-version "2.0.0")
