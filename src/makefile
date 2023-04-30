JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
		  Bank.java \
		  Board.java \
		  Card.java \
		  Deadwood.java \
		  Location.java \
		  LocationManager.java \
		  Player.java \
		  ProgressManager.java \
		  Role.java \
		  Scene.java \
		  Viewer.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
	
run:
	java Deadwood