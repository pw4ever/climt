.PHONY: all demo

GROUP := info.voidstar.tool.cli
ARTIFACT := climt
VERSION := 0.1.0-SNAPSHOT

# https://stackoverflow.com/a/5982798
THIS_MAKEFILE_PATH:=$(word $(words $(MAKEFILE_LIST)),$(MAKEFILE_LIST))
THIS_DIR:=$(shell cd $(dir $(THIS_MAKEFILE_PATH));pwd)
THIS_MAKEFILE:=$(notdir $(THIS_MAKEFILE_PATH))

BIN_DIR:=$(THIS_DIR)/bin
COURSIER_BIN=$(BIN_DIR)/coursier

export PATH:=$(BIN_DIR):$(PATH)

all: $(ARTIFACT)

$(ARTIFACT): $(COURSIER_BIN) $(THIS_DIR)/build.sbt $(shell find "$(THIS_DIR)/src" -type f \( -name '*.scala' -o -name '*.java' \))
	sbt publishLocal
	$(COURSIER_BIN) bootstrap -o $@ -f --standalone -M $@.Main "$(GROUP)::$(ARTIFACT):$(VERSION)"

$(COURSIER_BIN):
	mkdir -p $(BIN_DIR)
	src="https://git.io/coursier-cli"; \
        { 2>/dev/null hash curl && curl -Lo- "$$src" > "$@"; } || \
        { 2>/dev/null hash wget && wget -qO- "$$src" > "$@"; }
	chmod +x "$@"

demo: $(ARTIFACT)
	$< -h
	$< p -h
	$< p g
	$< p g -n -r 5
	$< p g -n 8 16 24 32
	$< p m CAFEbabe deadBEEF '($$_$$lol)'
