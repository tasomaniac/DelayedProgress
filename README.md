ContentLoadingProgressDialog
============================

ProgressDialog that waits a minimum time to be dismissed before showing. Once visible, the ProgressDialog will be visible for a minimum amount of time to avoid "flashes" in the UI.

##Usage

You can use it just like `ProgressDialog`. Just use `show()` and `dismiss()` appropriately and it will handle the rest. 

To create a `ContentLoadingProgressDialog` with default values

    ContentLoadingProgressDialog.showDelayed(context, title, message, indeterminate, cancelable);
    
You can use `makeDelayed()` to just create and show it manually. This way you can modify it easily

    ContentLoadingProgressDialog.makeDelayed(context, title, message, indeterminate, cancelable);
    
And configure

    ContentLoadingProgressDialog.makeDelayed(context, title, message, indeterminate, cancelable)
                                .minDelay(2000)
                                .minShowTime(500)
                                .show();
                                
The library also supports setter functions for easy usage.

    new ContentLoadingProgressDialog(this)
                .title(title)
                .message(message)
                .indeterminate(true)
                .cancelable(true)
                .minDelay(delay)
                .minShowTime(minShowTime)
                .show();


