package com.peacecorps.pcsa.circle_of_trust;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;

/*
 * Loads thumbnail from the phone number
 *
 * @author chamika
 * @since 2016-02-15
 */
public class ContactPhotoLoader extends AsyncTask<String, Integer, Bitmap> {

    private static final String TAG = ContactPhotoLoader.class.getSimpleName();

    private static final String[] PHOTO_ID_PROJECTION = {ContactsContract.Contacts.PHOTO_ID};
    private static final String[] PHOTO_BITMAP_PROJECTION = {ContactsContract.CommonDataKinds.Photo.PHOTO};

    private ImageView outputView;
    private Context context;

    @Override
    protected final Bitmap doInBackground(final String... params) {
        assert context != null : "context not set";
        assert outputView != null : "outputView not set";

        if (params != null && params.length == 1) {
            String phoneNumber = params[0];
            Integer thumbnailId = fetchThumbnailId(phoneNumber);
            if (thumbnailId != null) {
                return fetchThumbnail(thumbnailId);
            }
        }
        return null;
    }

    @Override
    protected final void onPostExecute(final Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap != null) {
            outputView.setImageBitmap(bitmap);
        }
    }

    /**
     * Fetch thumbnail ID for matching phone number
     *
     * @param phoneNumber
     * @return if matched, thumbnail ID, Else null
     */
    private Integer fetchThumbnailId(final String phoneNumber) {
        ContentResolver contentResolver = context.getContentResolver();

        final Uri baseUri = ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI;
        final Uri uri = Uri.withAppendedPath(baseUri, Uri.encode(phoneNumber));
        final String orderToSort = ContactsContract.Contacts.DISPLAY_NAME + " ASC";
        final Cursor cursor = contentResolver.query(uri, PHOTO_ID_PROJECTION, null, null, orderToSort);

        try {
            Integer thumbnailId = null;
            if (cursor != null && cursor.moveToFirst()) {
                String photoId = ContactsContract.Contacts.PHOTO_ID;
                int columnIndex = cursor.getColumnIndex(photoId);
                thumbnailId = cursor.getInt(columnIndex);
            }
            return thumbnailId;
        } catch (Exception e) {
            Log.e(TAG, "Unable to load thumbnail ID", e);
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    /**
     * Load thumbnail bitmap from thumbnail ID
     *
     * @param thumbnailId
     * @return
     */
    private Bitmap fetchThumbnail(final int thumbnailId) {
        ContentResolver contentResolver = context.getContentResolver();
        final Uri uri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, thumbnailId);
        final Cursor cursor = contentResolver.query(uri, PHOTO_BITMAP_PROJECTION, null, null, null);

        try {
            Bitmap thumbnail = null;
            if (cursor != null && cursor.moveToFirst()) {
                final byte[] thumbnailBytes = cursor.getBlob(0);
                if (thumbnailBytes != null) {
                    int length = thumbnailBytes.length;
                    thumbnail = BitmapFactory.decodeByteArray(thumbnailBytes, 0, length);
                }
            }
            return thumbnail;
        } catch (Exception e) {
            Log.e(TAG, "Unable to load thumbnail image", e);
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public final void setOutputView(final ImageView outputView) {
        this.outputView = outputView;
    }

    public final void setContext(final Context context) {
        this.context = context;
    }
}
