import ./Sprite

type
  MovieClip* = ref object of Sprite
    enabled*: bool
    totalFrames*: int

proc initMovieClip*(self: MovieClip) =
  self.initSprite()
  self.enabled = true
  self.totalFrames = 1

proc newMovieClip*(): MovieClip =
  let self = MovieClip()
  self.initMovieClip()
  return self

proc play*(self: MovieClip) = discard
proc stop*(self: MovieClip) = discard
proc gotoAndPlay*(self: MovieClip, frame: int) = discard
proc gotoAndStop*(self: MovieClip, frame: int) = discard
