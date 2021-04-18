// Snippets from a fix for Lazy Unbinding bug (Overposting)
// by Phi Lieu
using System;
using System.ComponentModel.DataAnnotations;

namespace program.Models
{
    public class BookView
    {
        public int? ID { get; set; }

        [Required]
        [MaxLength(80)]
        [MinLength(5)]
        public string Title { get; set;}
        
        [Display(Name = "Print Date")]
        [BookPrintDate]
        public DateTime PrintDate { get; set; }

        [Range(10.0, 1000.0)]
        public decimal Price { get; set; }

        public static BookView FromEntity(Book book)
        {
            return new BookView
            {
                ID = book.ID,
                Price = book.Price,
                PrintDate = book.PrintDate,
                Title = book.Title
            };
        }
    }
}

// Vulnerable part
//using System;
//using System.ComponentModel.DataAnnotations;
//using System.ComponentModel.DataAnnotations.Schema;
//
//namespace program.Models {
//    public class Book {
//        public int ID { get; set; }
//
//        [Required]
//        public string Title { get; set;}
//
//        [Display(Name = "Print Date")]
//        [DataType(DataType.Date)]
//        public DateTime PrintDate { get; set; }
//
//        [Column(TypeName = "decimal(18, 2)")]
//        public decimal Price { get; set; }
//
//        public bool isReviewed { get; set; }
//
//    }
//}

