def inputs = []

new File("day01.in").eachLine { it ->
  inputs.add(it as int)
}

def ret1 = 0

for(i in 0..inputs.size()-1) {
    if(inputs[i] < inputs[i+1]) ret1 += 1
}
println ret1

def windows = []

for(i in 0..inputs.size()-3) {
    windows.add(inputs[i..i+2].sum())
}

def ret2 = 0
for(i in 0..windows.size()-1) {
    if(windows[i] < windows[i+1]) ret2 += 1
}

println ret2