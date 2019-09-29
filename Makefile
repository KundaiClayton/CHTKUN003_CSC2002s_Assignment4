BINDIR=./bin
SRCDIR=./src
DOCDIR=./javadocs

.SUFFIXES: .java .class
.java.class:
	javac -cp ${BINDIR} -d ${BINDIR} $*.java

default: 
	javac -d ${BINDIR} ${SRCDIR}/*.java

clean:
	$(RM) ${BINDIR}/*.class

docs:
	javadoc  -classpath make${BINDIR} -d ${DOCDIR} ${SRCDIR}/*.java

cleandocs:
	rm -rf ${DOCDIR}/*
