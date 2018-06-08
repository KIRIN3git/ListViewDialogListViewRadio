package com.example.shinji.listviewdialoglistviewradio;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	ListView mPokemonListView;

	int mTrainerPosition;
	int mPokemonPosition;
	AlertDialog mAlt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListView mTrainerListView = (ListView) findViewById(R.id.mTrainerListView);

        /*
        // データを準備
        ArrayList<String> items = new ArrayList<>();
        for(int i = 0; i < 30; i++) {
            items.add("items-" + i);
        }
        */

		// 〇トレーナーリスト用
		ArrayList<Trainer> trainers = new ArrayList<>();

		int[] icons = {
				R.mipmap.ic_launcher,
				R.mipmap.ic_launcher,
				R.mipmap.ic_launcher,
				R.mipmap.ic_launcher,
				R.mipmap.ic_launcher,
				R.mipmap.ic_launcher,
		};

		String[] names = {
				"サトシ",
				"カスミ",
				"タケシ",
				"カスミ",
				"カスミ",
				"タケシ",
		};

		String[] comments = {
				"あああ",
				"いいい",
				"ううう",
				"いいい",
				"いいい",
				"ううう",
		};

		for (int i = 0; i < icons.length; i++) {
			Trainer trainer = new Trainer();
			trainer.setIcon(BitmapFactory.decodeResource(
					getResources(),
					icons[i]
			));

			trainer.setName(names[i]);
			trainer.setComment(comments[i]);
			trainers.add(trainer);

			Log.w( "DEBUG_DATA", "names[i] = " + names[i] );
			Log.w( "DEBUG_DATA", "comments[i] = " + comments[i] );
		}

		TrainerAdapter trai_adapter = new TrainerAdapter(this, 0, trainers);
		mTrainerListView.setAdapter(trai_adapter);



		// 〇モンスターリスト用
		mPokemonListView = new ListView(this);

		String[] priority = new String[icons.length];

		for (int i = 0; i < icons.length; i++) {
			priority[i] = String.valueOf(i+1);
		}


//		PokemonAdapter poke_adapter = new PokemonAdapter(this, 0, pokemons);
		ArrayAdapter poke_adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, priority); // ☆String型のときのみラジオボタンが可能、画像等追加する場合は複雑

		mPokemonListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		mPokemonListView.setAdapter(poke_adapter);


		// 〇トレーナーリストビューのクリックイベントを取得
		mTrainerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d("DEBUG_DATA", "onItemClick");

				// 最初からチェックkボックスを入れておく
				mPokemonListView.setItemChecked(position,true);

				mTrainerPosition = position;

				String msg = position + "番目のアイテムがクリックされました";
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();



				// ・ダイアログ設定
				AlertDialog.Builder alertDlg = new AlertDialog.Builder(MainActivity.this);
				alertDlg.setTitle("ポケモン");

				// 2回目起動時に、親のビューが設定されてしまっているので削除する。
				ViewGroup parent2 = (ViewGroup)mPokemonListView.getParent();
				if ( parent2 != null ) {
					parent2.removeView(mPokemonListView);
				}
				// ビューを設定
				alertDlg.setView(mPokemonListView);

				alertDlg.setPositiveButton(
						"OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								// OK ボタンクリック処理
							}
						});
				// 表示
				mAlt = alertDlg.create();
				mAlt.show();

			}
		});


		// 〇ポケモンリストビューのクリックイベントを取得
		mPokemonListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d("DEBUG_DATA", "onItemClick");
				
				mPokemonPosition = position;

				String msg = mTrainerPosition + "番目の" + mPokemonPosition + "番目のアイテムがクリックされました";
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
				mAlt.dismiss();

			}
		});
	}

	// 〇トレイナーアダプター
	public class TrainerAdapter extends ArrayAdapter<Trainer> {

		private LayoutInflater layoutInflater;
		public TrainerAdapter(Context c, int id, ArrayList<Trainer> trainers) {
			super(c, id, trainers);
			this.layoutInflater = (LayoutInflater) c.getSystemService(
					Context.LAYOUT_INFLATER_SERVICE
			);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = layoutInflater.inflate(
						R.layout.list_item,
						parent,
						false
				);
			}

			Trainer trainer = (Trainer) getItem(position);

			((ImageView) convertView.findViewById(R.id.icon))
					.setImageBitmap(trainer.getIcon());
			((TextView) convertView.findViewById(R.id.name))
					.setText(trainer.getName());
			((TextView) convertView.findViewById(R.id.comment))
					.setText(trainer.getComment());

			return convertView;
		}
	}

	// 〇ポケモンアダプター
	public class PokemonAdapter extends ArrayAdapter<Pokemon> {

		private LayoutInflater layoutInflater;
		public PokemonAdapter(Context c, int id, ArrayList<Pokemon> pokemons) {
			super(c, id, pokemons);
			this.layoutInflater = (LayoutInflater) c.getSystemService(
					Context.LAYOUT_INFLATER_SERVICE
			);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = layoutInflater.inflate(
						R.layout.list_item,
						parent,
						false
				);
			}

			Pokemon pokemon = (Pokemon) getItem(position);

			((ImageView) convertView.findViewById(R.id.icon))
					.setImageBitmap(pokemon.getIcon());
			((TextView) convertView.findViewById(R.id.name))
					.setText(pokemon.getName());
			((TextView) convertView.findViewById(R.id.comment))
					.setText(pokemon.getComment());

			return convertView;
		}
	}
}
