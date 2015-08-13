DelayedProgress
============================

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ContentLoadingProgressDialog-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2117)
[![Build Status](https://travis-ci.org/tasomaniac/DelayedProgress.png?branch=master)](https://travis-ci.org/tasomaniac/DelayedProgress)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

ProgressBar and ProgressDialog that waits a minimum time to be dismissed before showing.
Once visible, the they will be visible for a minimum amount of time to avoid "flashes" in the UI.

They extend platform version of them so that you can just replace all of your ProgressBar and ProgressDialog implementations quickly.

Usage
-----

You can use it just like `ProgressDialog`. Just use `show()` and `dismiss()` appropriately and it will handle the rest. 

To create a `DelayedProgressDialog` with default values

    DelayedProgressDialog.showDelayed(context, title, message, indeterminate, cancelable);
    
You can use `makeDelayed()` to just create and show it manually. This way you can modify it easily

    DelayedProgressDialog.makeDelayed(context, title, message, indeterminate, cancelable);
    
And configure

    DelayedProgressDialog.makeDelayed(context, title, message, indeterminate, cancelable)
                                .minDelay(2000)
                                .minShowTime(500)
                                .show();
                                
The library also supports setter functions for easy usage.

    new DelayedProgressDialog(this)
                .title(title)
                .message(message)
                .indeterminate(true)
                .cancelable(true)
                .minDelay(delay)
                .minShowTime(minShowTime)
                .show();


Download
--------

```groovy
compile 'com.tasomaniac:delayedprogress:0.2'
```

Snapshots of the development version are available in [Sonatype's `snapshots` repository][snap].



License
-------

    Copyright (C) 2015 Said Tahsin Dane

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.




 [snap]: https://oss.sonatype.org/content/repositories/snapshots/
