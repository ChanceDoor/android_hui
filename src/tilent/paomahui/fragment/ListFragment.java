package tilent.paomahui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tilent.paomahui.R;
import tilent.paomahui.fragment.ListFragment;
import tilent.paomahui.view.TitleView;
import tilent.paomahui.view.TitleView.OnLeftButtonClickListener;
import tilent.paomahui.view.TitleView.OnRightButtonClickListener;


public class ListFragment extends Fragment {

		private View mParent;
		
		private FragmentActivity mActivity;
		
		private TitleView mTitle;
		
		private TextView mText;
		
		/**
		 * Create a new instance of DetailsFragment, initialized to show the text at
		 * 'index'.
		 */
		public static ListFragment newInstance(int index) {
			ListFragment f = new ListFragment();

			// Supply index input as an argument.
			Bundle args = new Bundle();
			args.putInt("index", index);
			f.setArguments(args);

			return f;
		}

		public int getShownIndex() {
			return getArguments().getInt("index", 0);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.fragment_list, container, false);
			return view;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			mActivity = getActivity();
			mParent = getView();

			mTitle = (TitleView) mParent.findViewById(R.id.mtitle);
			mTitle.setTitle(R.string.title_list);
			mTitle.setLeftButton("", new OnLeftButtonClickListener(){

				@Override
				public void onClick(View button) {
					//mActivity.finish();
				}
				
			});
			/*mTitle.setRightButton("", new OnRightButtonClickListener() {

				@Override
				public void onClick(View button) {
					//goHelpActivity();
				}
			});*/
			
			//mText = (TextView) mParent.findViewById(R.id.fragment_list_text);

		}
		
		//private void goHelpActivity() {
			//Intent intent = new Intent(mActivity, HelpActivity.class);
			//startActivity(intent);
		//}

		@Override
		public void onHiddenChanged(boolean hidden) {
			super.onHiddenChanged(hidden);
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
		}
}
