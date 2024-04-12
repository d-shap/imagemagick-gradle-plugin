# ImageMagick Gradle Plugin
ImageMagick Gradle Plugin is a plugin to call ImageMagick CLI.

## Prerequisites
ImageMagick should be installed and `magick` command should be available:
```
magick logo: logo.gif
magick identify logo.gif
magick logo.gif win:
```

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

#### Draw an image with multiple image-processing operations
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
                destinationFile('magick.png')
            }
        }
    }
}
```
##### Executed command
```
magick -size 320x85 canvas:none -font Bookman-DemiItalic -pointsize 72 -draw "text 25,60 'Magick" -channel RGBA -blur 0x6 -fill darkred -stroke magenta -draw "text 25,60 'Magick" <<PROJECT_ROOT>>\build\gen\magick.png
```

#### Convert images to JPG and change size
##### Configuration
```
imagemagick {
    pipelines {
        jpgconvert {
            src 'src/main/resources/assets', { include '**/*.png' }
            dst 'gen'
            parameters {
                sourceFile()
                resize('20%')
                destinationFile({ name, ext -> "${name}.jpg" })
            }
        }
    }
}
```
##### Executed command
```
magick <<PROJECT_ROOT>>\src\main\resources\assets\texture\texture.png -resize 20% <<PROJECT_ROOT>>\build\gen\texture\texture.jpg
```

#### Resize an image with improved quality
##### Configuration
```
imagemagick {
    pipelines {
        resize {
            src 'src/main/resources/assets', { include '**/*.png' }
            dst 'gen'
            parameters {
                sourceFile()
                colorspace('RGB')
                sigmoidal({
                    prefix = '+'
                    args = ['-contrast', 11.6933]
                })
                define('filter:filter=Sinc')
                define('filter:window=Jinc')
                define('filter:lobes=3')
                resize('400%')
                sigmoidal({
                    prefix = '-'
                    args = ['-contrast', 11.6933]
                })
                colorspace('sRGB')
                destinationFile()
            }
        }
    }
}
```
##### Executed command
```
magick <<PROJECT_ROOT>>\src\main\resources\assets\texture\texture.png -colorspace RGB +sigmoidal-contrast 11.6933 -define filter:filter=Sinc -define filter:window=Jinc -define filter:lobes=3 -resize 400% -sigmoidal-contrast 11.6933 -colorspace sRGB <<PROJECT_ROOT>>\build\gen\texture\texture.png
```

# Donation
If you find my code useful, you can [bye me a coffee](https://www.paypal.me/dshapovalov)
