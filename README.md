# learning
Fiddling around with q-learning, sarsa learning, and sarsa-lambda learning algorithms.

### Description
This project contains implementations of q-learning, sarsa learning, and sarsa-lambda learning algorithms. Running `run.sh` with a specified number of runs and trials (each trial consists of an n number of runs) will execute the three algorithms on a simple maze and plot the number of steps it takes for each algorithm to arrive at the reward for each trial.

Over time it is expected that all three algorithms converge on the "optimal" solution. From the graphs it is evident that sarsa-lambda is most effective followed by sarsa and q-learning algorithms.

### To Run
- cd into the project folder
- give run.sh permission to run (e.g. `chmod u+x run.sh`)
- run run.sh with number of trials and runs (e.g. `./run.sh 200 10` will average out 20 trials of each learning algorithm with 200 runs per trial)

### Sample Graph
Below is a sample result from running the current version of the code with 20 trials (each with 200 runs).
![Alt text](/graphs/200x10.png?raw=true "Sample Plot of 20 trials with 200 runs")
