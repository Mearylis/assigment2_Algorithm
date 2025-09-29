# Linear Array Algorithms Implementation

## Pair 3: Boyer-Moore Majority Vote & Kadane's Algorithm

### Overview
This project implements two linear time complexity algorithms for array processing:
- **Boyer-Moore Majority Vote**: Finds majority elements in O(n) time and O(1) space
- **Kadane's Algorithm**: Finds maximum subarray sum in O(n) time and O(1) space

### Algorithms

#### Boyer-Moore Majority Vote
- **Time Complexity**: O(n)
- **Space Complexity**: O(1)
- **Features**:
  - Finds element appearing more than n/2 times
  - Extended version finds elements appearing more than n/3 times
  - Single-pass algorithm with candidate verification

#### Kadane's Algorithm
- **Time Complexity**: O(n)
- **Space Complexity**: O(1)
- **Features**:
  - Standard maximum subarray finding
  - Circular subarray support
  - Position tracking for subarray indices
  - Optimization for all-positive arrays

### Project Structure

src/
├── main/java/algorithms/
│ ├── array/
│ │ ├── BoyerMooreMajorityVote.java
│ │ └── KadaneAlgorithm.java
│ ├── metrics/
│ │ └── PerformanceTracker.java
│ └── cli/
│ ├── BenchmarkRunner.java
│ └── AdvancedBenchmarkRunner.java
└── test/java/algorithms/
└── array/
├── BoyerMooreMajorityVoteTest.java
└── KadaneAlgorithmTest.java
text


### Usage

#### Running Benchmarks

# Basic benchmarks
java algorithms.cli.BenchmarkRunner

# Comprehensive benchmarks with CSV output
java algorithms.cli.AdvancedBenchmarkRunner

Using the Algorithms


// Boyer-Moore Majority Vote
BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
Integer majority = bm.findMajority(array);
List<Integer> n3Majorities = bm.findMajorityElements(array);

// Kadane's Algorithm
KadaneAlgorithm kadane = new KadaneAlgorithm();
KadaneAlgorithm.Result result = kadane.findMaximumSubarray(array);
KadaneAlgorithm.Result circularResult = kadane.findMaximumCircularSubarray(array);
