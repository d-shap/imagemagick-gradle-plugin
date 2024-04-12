# ImageMagick Gradle Plugin
ImageMagick Gradle Plugin is a plugin to call ImageMagick CLI.

## Examples

#### Create a logo image
##### Configuration
```
imagemagick {
    pipelines {
        pipeline {
            dst 'gen'
            parameters {
                logo({
                    prefix = ''
                    args = [':']
                })
                destinationFile('logo.gif')
            }
        }
    }
}
```
##### Executed command
```
magick logo: <<PROJECT_ROOT>>\build\gen\logo.gif
```

#### Draw an image with draw commands
##### Configuration
```
imagemagick {
    pipelines {
        pipeline {
            dst 'gen'
            parameters {
                size('320x85')
                canvas({
                    prefix = ''
                    args = [':none']
                })
                font('Bookman-DemiItalic')
                pointsize(72)
                draw('text 25,60 \'Magick\'')
                channel('RGBA')
                blur('0x6')
                fill('darkred')
                stroke('magenta')
                draw('text 25,60 \'Magick\'')
                destinationFile('fuzzy-magick.png')
            }
        }
    }
}
```
##### Executed command
```
magick -size 320x85 canvas:none -font Bookman-DemiItalic -pointsize 72 -draw "text 25,60 'Magick" -channel RGBA -blur 0x6 -fill darkred -stroke magenta -draw "text 25,60 'Magick" E:\projects\android-projects\super-jumper\super-jumper-data\build\gen\fuzzy-magick.png
```

# Donation
If you find my code useful, you can [bye me a coffee](https://www.paypal.me/dshapovalov)
