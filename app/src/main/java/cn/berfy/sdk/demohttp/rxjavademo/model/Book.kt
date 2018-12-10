package cn.berfy.sdk.demohttp.rxjavademo.model

/**
 * Created by win764-1 on 2016/12/12.
 */

class Book {

    var count: Int = 0
    var start: Int = 0
    var total: Int = 0

    var books: List<BooksBean>? = null

    class BooksBean {

        var rating: RatingBean? = null
        var subtitle: String? = null
        var pubdate: String? = null
        var origin_title: String? = null
        var image: String? = null
        var binding: String? = null
        var catalog: String? = null
        var pages: String? = null
        var images: ImagesBean? = null
        var alt: String? = null
        var id: String? = null
        var publisher: String? = null
        var isbn10: String? = null
        var isbn13: String? = null
        var title: String? = null
        var url: String? = null
        var alt_title: String? = null
        var author_intro: String? = null
        var summary: String? = null
        var price: String? = null
        var author: List<String>? = null
        var tags: List<TagsBean>? = null
        var translator: List<String>? = null

        class RatingBean {
            var max: Int = 0
            var numRaters: Int = 0
            var average: String? = null
            var min: Int = 0

            override fun toString(): String {
                return "RatingBean{" +
                        "max=" + max +
                        ", numRaters=" + numRaters +
                        ", average='" + average + '\''.toString() +
                        ", min=" + min +
                        '}'.toString()
            }
        }

        class ImagesBean {
            var small: String? = null
            var large: String? = null
            var medium: String? = null

            override fun toString(): String {
                return "ImagesBean{" +
                        "small='" + small + '\''.toString() +
                        ", large='" + large + '\''.toString() +
                        ", medium='" + medium + '\''.toString() +
                        '}'.toString()
            }
        }

        class TagsBean {
            var count: Int = 0
            var name: String? = null
            var title: String? = null
        }

        override fun toString(): String {
            return "BooksBean{" +
                    "rating=" + rating +
                    ", subtitle='" + subtitle + '\''.toString() +
                    ", pubdate='" + pubdate + '\''.toString() +
                    ", origin_title='" + origin_title + '\''.toString() +
                    ", image='" + image + '\''.toString() +
                    ", binding='" + binding + '\''.toString() +
                    ", catalog='" + catalog + '\''.toString() +
                    ", pages='" + pages + '\''.toString() +
                    ", images=" + images +
                    ", alt='" + alt + '\''.toString() +
                    ", id='" + id + '\''.toString() +
                    ", publisher='" + publisher + '\''.toString() +
                    ", isbn10='" + isbn10 + '\''.toString() +
                    ", isbn13='" + isbn13 + '\''.toString() +
                    ", title='" + title + '\''.toString() +
                    ", url='" + url + '\''.toString() +
                    ", alt_title='" + alt_title + '\''.toString() +
                    ", author_intro='" + author_intro + '\''.toString() +
                    ", summary='" + summary + '\''.toString() +
                    ", price='" + price + '\''.toString() +
                    ", author=" + author +
                    ", tags=" + tags +
                    ", translator=" + translator +
                    '}'.toString()
        }
    }

    override fun toString(): String {
        return "Book{" +
                "count=" + count +
                ", start=" + start +
                ", total=" + total +
                ", books=" + books +
                '}'.toString()
    }
}
