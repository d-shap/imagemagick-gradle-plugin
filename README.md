# ImageMagick Gradle Plugin
ImageMagick Gradle Plugin is a plugin to call ImageMagick CLI.

## Prerequisites
ImageMagick should be installed and `magick` command should be available:
```
magick logo: logo.gif
magick identify logo.gif
magick logo.gif win:
```

## Configuration
Apply an appropriate version of a plugin:
```
plugins {
    id 'ru.d-shap.imagemagick' version '1.1'
}
```

Configure the plugin with an `imagemagick` block.
Configuration consist of a set of pipelines.
Each pipeline is a set of commands applied to a set of source files.
For example, one set of images should be converted from JPG to PNG format.
Another set of images should be resized.
```
imagemagick {
    pipelines {
        convert {
            ...
        }
        resize {
            ...
        }
    }
}
```

For each pipeline the following should be specified:
* a set of source files
* a destination folder
* a set of commands, applied to the source files

For example, to resize an image, next `magick` command is used:
```
magick rose.jpg -resize 50% rose.png
```
The configuration will be the following:
```
imagemagick {
    pipelines {
        resize {
            src 'source', { include 'rose.jpg' }
            dst 'gen'
            parameters {
                sourceFile()
                resize('50%')
                destinationFile('rose.png')
            }
        }
    }
}
```

### Source files
A command to pass a source file to `magick` command:
* `sourceFile()`

### Destination files
Commands to pass a destination file to `magick` command:
* `destinationFile()`
* `destinationFile('name.ext')`
* `destinationFile({ name, ext -> "${name}.${ext}" })`

### Parameters
Parameter name matches the function name in parameters block:
* `crop(geometry)`
* `monochrome()`
* `rotate(degrees)`

In this case parameters are passed with leading `-` character, for example `magick -crop geometry -monochrome -rotate degrees`

Some parameters don't have a leading `-` character, for example `canvas:none`.
In this case a closure should be passed to the function:
```
canvas({
    prefix = ''
    args = [':none']
})
```

Some parameters have `-` character in their name, for example `-contrast-stretch geometry`.
Character `-` is not allowed in function names, so function `contrast-stretch()` can't be defined.
So a `-` character should be replaced with `_` character:
```
contrast_stretch(geometry)
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

#### Draw an image with multiple image processing operations
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
                sigmoidal_contrast({
                    prefix = '+'
                    args = [11.6933]
                })
                define('filter:filter=Sinc')
                define('filter:window=Jinc')
                define('filter:lobes=3')
                resize('400%')
                sigmoidal_contrast({
                    prefix = '-'
                    args = [11.6933]
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
