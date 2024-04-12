# ImageMagick Gradle Plugin
ImageMagick Gradle Plugin is a plugin to call ImageMagick CLI.

## Examples

### Create a logo image
#### Configuration
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
#### Executed command
```
magick logo: <<PROJECT_ROOT>>\build\gen\logo.gif
```


# Donation
If you find my code useful, you can [bye me a coffee](https://www.paypal.me/dshapovalov)
