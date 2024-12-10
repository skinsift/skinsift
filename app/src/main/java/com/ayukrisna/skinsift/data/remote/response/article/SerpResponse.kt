package com.ayukrisna.skinsift.data.remote.response.article

import com.google.gson.annotations.SerializedName

data class SerpResponse(

	@field:SerializedName("news_results")
	val newsResults: List<NewsResultsItem?>? = null,

	@field:SerializedName("pagination")
	val pagination: Pagination? = null,

	@field:SerializedName("search_metadata")
	val searchMetadata: SearchMetadata? = null,

	@field:SerializedName("search_information")
	val searchInformation: SearchInformation? = null,

	@field:SerializedName("search_parameters")
	val searchParameters: SearchParameters? = null,

	@field:SerializedName("serpapi_pagination")
	val serpapiPagination: SerpapiPagination? = null
)

data class NewsResultsItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("snippet")
	val snippet: String? = null,

	@field:SerializedName("thumbnail")
	val thumbnail: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("position")
	val position: Int? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)

data class Pagination(

	@field:SerializedName("next")
	val next: String? = null,

	@field:SerializedName("current")
	val current: Int? = null,

	@field:SerializedName("other_pages")
	val otherPages: OtherPages? = null
)

data class SearchMetadata(

	@field:SerializedName("raw_html_file")
	val rawHtmlFile: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("processed_at")
	val processedAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("total_time_taken")
	val totalTimeTaken: Any? = null,

	@field:SerializedName("google_url")
	val googleUrl: String? = null,

	@field:SerializedName("json_endpoint")
	val jsonEndpoint: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class SearchInformation(

	@field:SerializedName("query_displayed")
	val queryDisplayed: String? = null,

	@field:SerializedName("time_taken_displayed")
	val timeTakenDisplayed: Any? = null,

	@field:SerializedName("news_results_state")
	val newsResultsState: String? = null,

	@field:SerializedName("total_results")
	val totalResults: Int? = null
)

data class SerpapiPagination(

	@field:SerializedName("next")
	val next: String? = null,

	@field:SerializedName("current")
	val current: Int? = null,

	@field:SerializedName("other_pages")
	val otherPages: OtherPages? = null,

	@field:SerializedName("next_link")
	val nextLink: String? = null
)

data class OtherPages(

	@field:SerializedName("2")
	val jsonMember2: String? = null,

	@field:SerializedName("3")
	val jsonMember3: String? = null,

	@field:SerializedName("4")
	val jsonMember4: String? = null,

	@field:SerializedName("5")
	val jsonMember5: String? = null,

	@field:SerializedName("6")
	val jsonMember6: String? = null,

	@field:SerializedName("7")
	val jsonMember7: String? = null,

	@field:SerializedName("8")
	val jsonMember8: String? = null,

	@field:SerializedName("9")
	val jsonMember9: String? = null,

	@field:SerializedName("10")
	val jsonMember10: String? = null
)

data class SearchParameters(

	@field:SerializedName("location_requested")
	val locationRequested: String? = null,

	@field:SerializedName("q")
	val q: String? = null,

	@field:SerializedName("hl")
	val hl: String? = null,

	@field:SerializedName("tbs")
	val tbs: String? = null,

	@field:SerializedName("engine")
	val engine: String? = null,

	@field:SerializedName("gl")
	val gl: String? = null,

	@field:SerializedName("google_domain")
	val googleDomain: String? = null,

	@field:SerializedName("safe")
	val safe: String? = null,

	@field:SerializedName("location_used")
	val locationUsed: String? = null,

	@field:SerializedName("device")
	val device: String? = null,

	@field:SerializedName("tbm")
	val tbm: String? = null
)
