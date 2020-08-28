Allen Mumford
CS 3345.003
Fall 2018
Project 5

I used Eclipse.

[Pivot Selection Method]: [Time taken in nanoseconds for 100 elements], [1000], [5000], [10000], [50000]

First Element: 35739, 373789, 1523601, 3819578, 12101650
Random Element: 56160, 506530, 1852536, 4980329, 23122047
Median of 3 Random Elements: 151339, 522211, 2221584, 5218825, 12224180
Median of First, Last, and Middle: 35373, 389105, 1412011, 4190449, 22582697

All of these methods, when run at 50000 repeatedly, seem to fluctuate from ~8-30 million nanoseconds. 
If we assume that the Random function in java is fairly good, then this makes sense. This would be very 
different if the list were already in some particular order, but in a well-randomized list, it isn't as
important.