## qrng

Random number generation via the [ANU Quantum Random Numbers Server](http://qrng.anu.edu.au/index.php).  
These are purportedly 'truly random' numbers, based on measuring the  
quantum fluctuations of the vacuum.

### Usage

**(qrand & opts)**

You have 3 choices for option **:type**:  
* __:int8__   (an integer between 0-255; the default)  
* __:int16__   (an integer between 0-65535)  
* __:hex16__   (a hexadecimal string between 00-ff)  
  
The **:blocks** option is relevant only for type :hex16.  
It sets the hexadecimal block size and must be a number  
between 1-1024. The default is 1.

The **:length** option is the number of random numbers  
to be returned. The default is 1.

If option **:https** is true, the connection will be  
established using the https protocol. Note, however,  
that presently the ANU server has an untrusted SSL cert.  
The default is false (i.e., http is used).

### Examples

```clj
(require '[naipmoro.qrng.core :as qrng])
```

One random integer between 0-255:  

```clj
(qrng/qrand)  
=> [110]
```

6 random integers between 0-255:  
 
```clj
(qrng/qrand :length 6)  
=> [67 225 141 118 46 102]
```

4 random integers between 0-65535:  

```clj  
(qrng/qrand :length 4, :type :int16)  
=> [24861 25422 60585 58192]
```

6 random hexadecimal strings (00-ff):  

```clj  
(qrng/qrand :length 6, :type :hex16)  
=> ["f8" "45" "3b" "78" "06" "20"]
```

5 hexadecimal blocks of size 2 (0000-ffff):  
  
```clj
(qrng/qrand :length 5, :type :hex16, :blocks 2)  
=> ["07cb" "fcc6" "411b" "2780" "3a99"]
```

Connect to server using the https protocol:  

```clj
(qrng/qrand :length 5, :type :hex16, :blocks 2, :https true)  
=> ["d34d" "7b40" "8c35" "3693" "fb2c"]
```

1024 bytes of random hexadecimals:  

```clj
(qrng/qrand  :type :hex16, :blocks 1024)  
=> ["554681a2c8a6<<snipped 2024 characters>>16faddb80522"]
```
### Acknowledgments

Thanks to the folks at **qrng.anu.edu.au** for allowing the public  
free access to their random number generator.

### License

Distributed under the Eclipse Public License, the same as Clojure.