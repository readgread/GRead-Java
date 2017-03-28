package com.gread;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.gread.HomeActivity.appContext;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //ScrawledFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScrawledFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScrawledFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseAnalytics mFireBaseAnalytics;
    RecyclerView scrawled_recView;
    public static JSONArray scrawledResultSet;
    RecyclerView.LayoutManager scrawled_rev_layout_mgr;

   // private OnFragmentInteractionListener mListener;

    public ScrawledFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScrawledFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance(String param1, String param2) {
        Fragment fragment = new ScrawledFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mFireBaseAnalytics = FirebaseAnalytics.getInstance(appContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_scrawled, container, false);
        scrawled_recView = ((RecyclerView)rootView.findViewById(R.id.scrawled_recycler));
        scrawled_rev_layout_mgr = new LinearLayoutManager(appContext);
        scrawled_recView.setLayoutManager(scrawled_rev_layout_mgr);
        CommasAdapter adapterReader = null;
        try {
            adapterReader = new CommasAdapter(appContext, getImages());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scrawled_recView.setAdapter(adapterReader);
        // Click listener on images.
        scrawled_recView.addOnItemTouchListener(
                new RecyclerItemClickListener(appContext, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Bundle params = new Bundle();
                        params.putString("username", mParam1);
                        params.putString("emailId", mParam2);
                        params.putString("image", "scarwled_stories");
                        params.putString("pageId", "3");
                        params.putString("imageId", String.valueOf(scrawled_recView.getId()));
                        params.putString("position", String.valueOf(position));
                        //params.putString("imageURL", commas_recView.getAdapter().images.get(position).imageURL);
                        mFireBaseAnalytics.logEvent("touch_image", params);
                        mFireBaseAnalytics.setUserProperty("Scrawled_readers", mParam1 + "**" + mParam2);
                    }
                })
        );
        return rootView;
    }

    public List<ImageParser> getImages() throws ExecutionException, InterruptedException {
        List<ImageParser> allImages=new ArrayList();
        HttpURLConnection connection = null;
        BufferedReader reader=null;
        try {
            URL url = new URL("http://139.59.19.54/scrawled.json");
            connection = (HttpURLConnection)url.openConnection();
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line=reader.readLine()) != null){
                buffer.append(line+"\n");
            }
            scrawledResultSet = new JSONArray(buffer.toString());
        }
        catch (MalformedURLException e){
            e.printStackTrace();
            Bundle params = new Bundle();
            params.putString("exception", e.getMessage());
            mFireBaseAnalytics.logEvent("exception_scrawled", params);
        } catch (IOException e) {
            e.printStackTrace();
            Bundle params = new Bundle();
            params.putString("exception", e.getMessage());
            mFireBaseAnalytics.logEvent("exception_scrawled", params);
        } catch (JSONException e) {
            e.printStackTrace();
            Bundle params = new Bundle();
            params.putString("exception", e.getMessage());
            mFireBaseAnalytics.logEvent("exception_scrawled", params);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Bundle params = new Bundle();
                params.putString("exception", e.getMessage());
                mFireBaseAnalytics.logEvent("exception_scrawled", params);
            }

        }
        try {
            for(int i=0;i<scrawledResultSet.length();i++){
                ImageParser imageParser =new ImageParser();
                imageParser.imageURL="http://139.59.19.54/scrawled50/"+scrawledResultSet.getString(i);
                //System.out.println(imageParser.imageURL);
                allImages.add(imageParser);
            }
            //System.out.println(allImages.get(0).imageURL);
        } catch (JSONException e) {
            e.printStackTrace();
            Bundle params = new Bundle();
            params.putString("exception", e.getMessage());
            mFireBaseAnalytics.logEvent("exception_scrawled", params);
        }
        return allImages;
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
  //      mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
