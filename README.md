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

DelayedProgressBar
------------------

`DelayedProgressBar` is an extension to `ProgressBar` that handles automatic delaying and prevents flashes in the UI.

You can create it programatically just like you do with `ProgressBar` or you can use it in your XMLs.

    <com.tasomaniac.android.widget.DelayedProgressBar
        android:id="@android:id/progress"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        style="?android:progressBarStyleHorizontal"/>

And you have to use `show()` and `hide()` functions instead of setting its visibility manually to have awesome delaying functionality.

Additionally we have `show(boolean animate)` and `hide(boolean animate)` that fades in and out the `ProgressBar`.

    progressbar.show(true);

Lastly, if you want to do something when the animation ends, you can use the below function with endAction.

    progressbar.show(true, new Runnable() {
        @Override
        public void run() {
            //Do something
        }
    });

    progressbar.hide(true, new Runnable() {
        @Override
        public void run() {
            //Do something
        }
    });

DelayedProgressDialog
---------------------

`DelayedProgressDialog` is an extension to `ProgressDialog` that handles automatic delaying and prevents flashes in the UI.
You can use it just like `ProgressDialog`. Just use `show()` and `dismiss()` appropriately and it will handle the rest.

To create a `DelayedProgressDialog` with default timing values

    DelayedProgressDialog.showDelayed(context, title, message, indeterminate, cancelable);
    
You can use `make()` to just create (without calling `show()`) it manually. This way you can modify it easily before calling `show()`

    DelayedProgressDialog.makeDelayed(context, title, message, indeterminate, cancelable);
    
And configure

    DelayedProgressDialog dialog = DelayedProgressDialog.make(...);
    dialog.setMinDelay(2000);
    dialog.setMinShowTime(500);
    dialog.show();

Download
--------

```groovy
compile 'com.tasomaniac:delayed-progress:0.3'
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
