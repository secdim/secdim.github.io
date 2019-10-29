![Jekyll Version](https://img.shields.io/gem/v/jekyll.svg)

# Website

## Build
```
export JEKYLL_VERSION=3.5
docker run --name newblog --volume="$PWD:/srv/jekyll" -p 8000:4000 -it jekyll/jekyll:$JEKYLL_VERSION jekyll serve --watch --drafts
```
