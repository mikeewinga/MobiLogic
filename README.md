# MobiLogic
This is an archive of a previously completed university project.
*Note: This project was completed with a group of six people.*
   
MobiLogic is a logic simulator built in Codename One. This logic simulator 
would allow users to experiment with simple logic circuits in real time and 
get immediate feedback on their understanding.
   
## Design Document

## Introduction
### Background
This application is intended to be a basic logic simulator similar in purpose
to programs like Logisim but less in scope. Mobilogic is built in Codename One
with a set of core requirements such as, all basic logic implementation, arbitrary
small circuits that can be saved and loaded as a sub-circuit to be used in the
design of another, propagation delay, and color coded wires to indicate true false
and no-connect states. These features are included under the assumption that
this application is not meant to replace more in depth logic simulators such as
Verilog but to extend one's own intuition and help streamline the process of
evaluation logic expressions.
### Typical customer
The typical customer for this application would likely be a student in un-
dergrad taking discrete mathematics or possibly an upper division computer
architecture course who is familiar with solving logic expressions but who needs
a quick and simple way to validate their intuition. The interface is simple enough
however to allow more novice customers looking to experiment with basic logic
simulations.

## Design
### Display
The display for the application is made up of a single container with a ridged
10 by 20 cell grid. The menus at the top left and right sides of the screen were
designed so that the maximum amount of display could be used by the actual
grid rather than being taken up by a list of options and grid object. This way
the user is able to easily tap the menus, choose an option, and it will close
automatically when an option is picked.   
The Grid is the main component of the grid you see on the screen. Inside of
Grid is an instance of GridObject and all the necessary functions that use the
paint(Graphics g) function to display the 10x20 grid, display the grid objects,
and enable cell highlighting.  
The GridObject class holds the CellMatrix, which is the 2D array that con-
tains the object data for every cell in the grid and the currentCell object which
contains data for the currently highlighted cell. The GridObject also contains
all the functions used for connecting each cell based on their position, direction,
and adjacency.
### Cell Objects
The cell objects are the individual cells inside the CellMatrix, including the
wires, gates, and inputs. Each cell object extends from the root class, Cell. Cell
contains a very basic set of variables including its position on the grid, its ID,
and its current Boolean output. Every cell in the grid, including the empty
cells, are an instance of the Cell class.  
The Item class is an extension of Cell, containing all of its properties and
adding new ones including an Image, the direction it's currently facing, and a
list of the cells it is connected to. The Item class then extends to the three main
support classes: SinglePortItem, DoublePortItem, and TriplePortItem.  
These classes differ in the total number of ports, or inputs and out-
puts, they contain. For instance, SinglePortItem is used primarily for the in-
put/output objects like Input and LED were, they only have a single connection.
DoublePortItem has both an input and an output, to account for objects that
pass or modify some Boolean value like the wires and the NOT gate. The
TriplePortItem contains two inputs and one output. This is used for most of
the logical gates in the application.  
These three classes are extended further to reduce the amount of repetitive
code to form the leaf classes of each individual wire-type or gate-type. Those
final classes are the ones that are stored in the CellMatrix in the GridObject
class.  

### Adding Components
Adding a component is both simple and fast. By highlighting the cell in
which you wish to place the component and navigating to the Item List and
choosing which component will be placed in that cell the component is added to
the board. Chaining multiple components together is done by either placing a
component directly adjacent to another, or by using a combination of the 
different wires available you can place multiple components within the grid. 
The user can also manipulate these components. Once highlighted the user can choose
to delete, rotate, or mirror the selected component with the buttons on the bottom
of the grid.  

### Save and Load
Saving your circuit is as easy as selecting which save slot the user would like
to save the current circuit into. The program can hold up to at most 4 circuits.
In order to load a saved circuit to the current grid the user would select the
load button that corresponds to the save slot you would like to access.   
This works by storing the GridObject into a list and accessing them based
on the key for which slot the user would like to save or load from. If there was
nothing saved in the slot that the user is trying to load from then nothing will
happen.


### Sub-circuit implementation
Unfortunately we never were able to get our implementation of the sub-
circuit to run correctly. This issue arises when we attempt to do deep copies of
all the cells in the cellMatrix so that when a new circuit is created, the connect
functions were able to move through the sub-circuit as if the circuit was actually
present in the circuit. This issue stems from the fact that java copies objects
by reference and not the actual abject.

### Wires and Connections
We were able to get the wires connect and they are able to change between
blue (off), green (on), and red (error/unconnected) wires depending on the
output they currently hold. To do this we have separate images for each wire
type and color. When a wire updates with a new output the image is swapped
out with the corresponding colored image.    
All the placeable items find their connections by adjusting their which cells
they look at for connection. This process is based on the item's specific shape
and orientation.   
Each connection will subsequently move through the its inputs and outputs
until it reaches the LED item. While it moves through this path, it iteratively
updates the current output of the entire circuit, and sets the LED to whatever
the output is at the end of the circuit path.   

### Propagation Delay
The user can easily enter the delay values for all the gates when executing
a long press on the cell you wish to add a delay to. The user can then view the
propagation delay for the output.
