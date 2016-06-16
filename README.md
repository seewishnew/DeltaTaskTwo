# Task Two

## Synopsis: 

The app consists of a smiley face that can change its emotion based on voice inputs. The voice inputs can also make the smiley
move across the screen (if there's enough space). 

The new feature allows the smiley face to animate across the screen like a screensaver.

## Tests:

The app has mic button at the bottom, which can be used to start the recognizer intent to listen for voice input. The voice input
is then displayed as a toast message at the bottom of the screen (to avoid frustration in cases when the interpreted input is not
the same as the intended input). If there's no command, then an error toast message pops up. 

When it comes to changing the emotions, it basically listens for keywords like 'smile', 'laugh', 'happy', 'normal','default' and 
'sad'or a censored swear word. Play around with it to see what all it responds to.

When it comes to directions for it to move, it looks for basic directions like, 'up', 'down', 'left', 'right'. These can be a part
of a bigger sentence. For example, you can say, "Please move right." And it'll do so if there is enough space to move right.

When it comes to cardinal directions, however, as replacements for the basic directions, you can say things like, "North", "South",
"East" and "West", but it'll only accept these words as input. For example, "Please move North" will *not* be interpreted properly.

However, you can give directions like, "Northeast", "Southwest", etc. and these can be part of a larger sentence. If there is no 
response, then try saying just the directions to see if it will respond.

In case the smiley gets to the end of the bitmap (the red rectangle) a toast message will appear saying that it cannot move for
there is no space left. While there may seem to be a bit of space left near some edges, the emoji will not be able to move, as
the movement is controlled by a certain number of pixels that is dependent on screen size.

The new feature animates the smiley face across the screen and bounces across the borders, like a screensaver. This mode is called upon the voice command, "bounce." To stop, you can simply say, "stop."

Note that you can also give other commands to the smiley while this animation keeps going on.

All the dimensions of all the objects in the app are dependent on screen dimensions. So it should adapt to different screen sizes
properly.



