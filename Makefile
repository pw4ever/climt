.PHONY: all demo

GROUP := info.voidstar.tool.cli
ARTIFACT := climt
VERSION := 0.1.0-SNAPSHOT

# https://stackoverflow.com/a/5982798
THIS_MAKEFILE_PATH:=$(word $(words $(MAKEFILE_LIST)),$(MAKEFILE_LIST))
THIS_DIR:=$(shell cd $(dir $(THIS_MAKEFILE_PATH));pwd)
THIS_MAKEFILE:=$(notdir $(THIS_MAKEFILE_PATH))

all: climt

climt: coursier $(THIS_DIR)/build.sbt $(shell find "$(THIS_DIR)/src" -type f \( -name '*.scala' -o -name '*.java' \))
	sbt publishLocal
	./coursier bootstrap -o climt -f --standalone -M climt.Main "$(GROUP)::$(ARTIFACT):$(VERSION)"

coursier:
	src="https://git.io/coursier-cli"; \
        { 2>/dev/null hash curl && curl -Lo- "$$src" > "$@"; } || \
        { 2>/dev/null hash wget && wget -qO- "$$src" > "$@"; }
	chmod +x "$@"

demo: climt
	./climt -h
	./climt gp -h
	./climt gp -n
