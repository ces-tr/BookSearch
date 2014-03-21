

package com.Android.enums;


public enum SearchType {

	   author("author", 2) {
		/*@Override
		public String getTitle() {
			
			    return "Author";
		}*/
	},
	   isbn("isbn", 0) {
		/*@Override
		public String getTitle() {
			
			 return "ISBN";
		}*/
	},
	   keyword("keyword", 3) {
		/*@Override
		public String getTitle() {
			
			  return "Keyword";
		}*/
	},
	   title("title", 1) {
		/*@Override
		public String getTitle() {
			
			return "Title";
		}*/
	}, 
	ALL_FIELDS("ALL_FIELDS", 1) {
		/*@Override
		public String getTitle() {
			// TODO Auto-generated method stub
			return null;
		}*/
	};
	   
	   private static final SearchType[] ENUM$VALUES;

	   static {
	      SearchType[] var0 = new SearchType[]{isbn, title, author, keyword};
	      ENUM$VALUES = var0;
	   }

	   private SearchType(String var1, int var2) {}

	   
	   SearchType(String var1, int var2, SearchType var3) {
	      this(var1, var2);
	   }

	   //public abstract String getTitle();
	}