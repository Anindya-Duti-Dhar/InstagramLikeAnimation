package anindya.sample.InstagramLikeAnimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.FloatMath;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class FragmentB extends Fragment {

    //Defining Variables
    String TAG = getClass().getName();
    public ImageView leftImage, rightImage, leftImageLike, rightImageLike;
    View leftImageLikeBg, rightImageLikeBg;

    public static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    public static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();

    // These matrices will be used to move and zoom image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    String savedItemClicked;

    // create instance
    public static FragmentB newInstance() {
        FragmentB fragment = new FragmentB();
        fragment.setRetainInstance(true);
        return fragment;
    }

    public FragmentB() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // start view initialize and functionality declare from here

        leftImage = (ImageView)view.findViewById(R.id.left_box);
        rightImage = (ImageView)view.findViewById(R.id.right_box);

        leftImageLike = (ImageView)view.findViewById(R.id.left_image_like);
        rightImageLike = (ImageView)view.findViewById(R.id.right_image_like);

        leftImageLikeBg = (View)view.findViewById(R.id.left_image_bg_like);
        rightImageLikeBg = (View)view.findViewById(R.id.right_image_bg_like);

        leftImage.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    animatePhotoLike("left");
                    return super.onDoubleTap(e);
                }

                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    showImage(R.drawable.left_demo);
                    return super.onSingleTapConfirmed(e);
                }
            });


            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });

        rightImage.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    animatePhotoLike("right");
                    return super.onDoubleTap(e);
                }

                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    showImage(R.drawable.right);
                    return super.onSingleTapConfirmed(e);
                }
            });


            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });


    }

    private void animatePhotoLike(String boxSide) {
        final String boxType = boxSide;
        View vImageBgLike = null;
        ImageView likeImage = null;
        if (boxType.equals("left")) {
            vImageBgLike = leftImageLikeBg;
            likeImage = leftImageLike;
        } else {
            vImageBgLike = rightImageLikeBg;
            likeImage = rightImageLike;
        }
        vImageBgLike.setVisibility(View.VISIBLE);
        likeImage.setVisibility(View.VISIBLE);

        vImageBgLike.setScaleY(0.1f);
        vImageBgLike.setScaleX(0.1f);
        vImageBgLike.setAlpha(1f);
        likeImage.setScaleY(0.1f);
        likeImage.setScaleX(0.1f);

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator bgScaleYAnim = ObjectAnimator.ofFloat(vImageBgLike, "scaleY", 0.1f, 1f);
        bgScaleYAnim.setDuration(300);
        bgScaleYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
        ObjectAnimator bgScaleXAnim = ObjectAnimator.ofFloat(vImageBgLike, "scaleX", 0.1f, 1f);
        bgScaleXAnim.setDuration(300);
        bgScaleXAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
        ObjectAnimator bgAlphaAnim = ObjectAnimator.ofFloat(vImageBgLike, "alpha", 1f, 0f);
        bgAlphaAnim.setDuration(300);
        bgAlphaAnim.setStartDelay(250);
        bgAlphaAnim.setInterpolator(DECCELERATE_INTERPOLATOR);

        ObjectAnimator imgScaleUpYAnim = ObjectAnimator.ofFloat(likeImage, "scaleY", 0.1f, 1f);
        imgScaleUpYAnim.setDuration(300);
        imgScaleUpYAnim.setInterpolator(DECCELERATE_INTERPOLATOR);
        ObjectAnimator imgScaleUpXAnim = ObjectAnimator.ofFloat(likeImage, "scaleX", 0.1f, 1f);
        imgScaleUpXAnim.setDuration(300);
        imgScaleUpXAnim.setInterpolator(DECCELERATE_INTERPOLATOR);

        ObjectAnimator imgScaleDownYAnim = ObjectAnimator.ofFloat(likeImage, "scaleY", 1f, 0f);
        imgScaleDownYAnim.setDuration(300);
        imgScaleDownYAnim.setInterpolator(ACCELERATE_INTERPOLATOR);
        ObjectAnimator imgScaleDownXAnim = ObjectAnimator.ofFloat(likeImage, "scaleX", 1f, 0f);
        imgScaleDownXAnim.setDuration(300);
        imgScaleDownXAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

        animatorSet.playTogether(bgScaleYAnim, bgScaleXAnim, bgAlphaAnim, imgScaleUpYAnim, imgScaleUpXAnim);
        animatorSet.play(imgScaleDownYAnim).with(imgScaleDownXAnim).after(imgScaleUpYAnim);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                resetLikeAnimationState(boxType);
            }
        });
        animatorSet.start();
    }

    private void resetLikeAnimationState(String boxSide) {
        String boxType = boxSide;
        View vImageBgLike = null;
        ImageView likeImage = null;
        if (boxType.equals("left")) {
            vImageBgLike = leftImageLikeBg;
            likeImage = leftImageLike;
        } else {
            vImageBgLike = rightImageLikeBg;
            likeImage = rightImageLike;
        }
        vImageBgLike.setVisibility(View.INVISIBLE);
        likeImage.setVisibility(View.INVISIBLE);
    }

    public void showImage(int drawable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog dialog = builder.create();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogLayout = inflater.inflate(R.layout.full_screen_image, null);
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        ImageView image = (ImageView) dialogLayout.findViewById(R.id.fullImage);
        Picasso.with(getActivity())
                .load(drawable)
                .placeholder(R.drawable.placeholder_image) //.resize(imgWidth, imgHeight) .centerCrop()
                .into(image);

        // pinch zooming
        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView view = (ImageView) v;
                view.setScaleType(ImageView.ScaleType.MATRIX);
                dumpEvent(event);

                // Handle touch events here...
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN: // first finger down only
                        savedMatrix.set(matrix);
                        start.set(event.getX(), event.getY());
                        Log.d(TAG, "mode=DRAG");
                        mode = DRAG;
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down
                        oldDist = spacing(event);
                        Log.d(TAG, "oldDist=" + oldDist);
                        if (oldDist > 10f) {
                            savedMatrix.set(matrix);
                            midPoint(mid, event);
                            mode = ZOOM;
                            Log.d(TAG, "mode=ZOOM");
                        }
                        break;
                    case MotionEvent.ACTION_UP: // first finger lifted
                    case MotionEvent.ACTION_POINTER_UP:  // second finger lifted
                        mode = NONE;
                        Log.d(TAG, "mode=NONE");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAG) {
                            // ...
                            matrix.set(savedMatrix);
                            matrix.postTranslate(event.getX() - start.x, event.getY()
                                    - start.y);
                        } else if (mode == ZOOM) {
                            // pinch zooming
                            float newDist = spacing(event);
                            Log.d(TAG, "newDist=" + newDist);
                            if (newDist > 10f) {     // you can set it 5f and test it is that scaling
                                matrix.set(savedMatrix);
                                float scale = newDist / oldDist;
                                // setting the scaling of the
                                // matrix...if scale > 1 means
                                // zoom in...if scale < 1 means
                                // zoom out
                                matrix.postScale(scale, scale, mid.x, mid.y);
                            }
                        }
                        break;
                }

                view.setImageMatrix(matrix);
                return true;
            }
        });

        dialog.show();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {

            }
        });
    }

    /** Show an event in the LogCat view, for debugging */
    private void dumpEvent(MotionEvent event) {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
                "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);
        if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(
                    action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }
        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }
        sb.append("]");
        Log.d(TAG, sb.toString());
    }

     /*
     * --------------------------------------------------------------------------
     * Method: spacing Parameters: MotionEvent Returns: float Description:
     * checks the spacing between the two fingers on touch
     * ----------------------------------------------------
     */

    /** Determine the space between the first two fingers */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float)Math.sqrt(x * x + y * y);
    }

    /*
     * --------------------------------------------------------------------------
     * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
     * Description: calculates the midpoint between the two fingers
     * ------------------------------------------------------------
     */

    /** Calculate the mid point of the first two fingers */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

}
