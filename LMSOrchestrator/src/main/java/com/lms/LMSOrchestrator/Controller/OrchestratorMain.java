package com.lms.LMSOrchestrator.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lms.LMSOrchestrator.POJO.Author;
import com.lms.LMSOrchestrator.POJO.Book;
import com.lms.LMSOrchestrator.POJO.BookLoans;
import com.lms.LMSOrchestrator.POJO.Borrower;
import com.lms.LMSOrchestrator.POJO.LibraryBranch;
import com.lms.LMSOrchestrator.POJO.Publisher;

@RestController
@RequestMapping("/orchestrator")
public class OrchestratorMain {
    String borrowerUri = "http://localhost:8090";
    
    @Autowired
    RestTemplate restTemp = new RestTemplate();

    //Borrower checkout
    @PutMapping (value = "/borrower/checkout",
    		consumes = {"application/xml", "application/json"})
    
    public ResponseEntity<BookLoans> mainWriteLoans(@RequestHeader("Accept") String accept,
    		@RequestHeader("Content-Type") String contentType,
    		@RequestBody BookLoans bookLoans)
    		{    
	    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
	    headers.add("Content-Type", contentType);
	    headers.add("Accept", accept);
	    HttpEntity<BookLoans> request = new HttpEntity<BookLoans>(bookLoans, headers); 
	    try {
		    return restTemp.exchange(
		    		borrowerUri+"/borrower/checkout",
		    		HttpMethod.PUT,  request,
		    		BookLoans.class  );
		}
	    catch(HttpStatusCodeException e) {
	        return new ResponseEntity<BookLoans> (e.getStatusCode());
	    }
    }  

    //Borrower return
    @PutMapping (value = "/borrower/return", 
    		consumes= {"application/xml", "application/json"})
    
    public ResponseEntity<BookLoans> mainWriteReturn(@RequestHeader("Accept") String accept,
    		@RequestHeader("Content-Type") String contentType,
    		@RequestBody BookLoans bookLoans)
    		{
	    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
	    headers.add("Content-Type", contentType);
	    headers.add("Accept", accept);
	    HttpEntity<BookLoans> request = new HttpEntity<BookLoans>(bookLoans, headers);
	    
	    try {
		    return restTemp.exchange(
		    borrowerUri+"/borrower/return", 
		    HttpMethod.PUT,  request,
		    BookLoans.class);
	    }
		catch(HttpStatusCodeException e) {
		    return new ResponseEntity <BookLoans> (e.getStatusCode());
		}
    }
//    
//    
// 
//    
//    @PutMapping
//	(value = "/LMSLibrarian/branches/{branchId}/name/{name}/address/{address}", consumes = {"application/xml", "application/json"})
//	public ResponseEntity<LibraryBranch> UpdateBranch(@RequestHeader("Accept") String accept,
//			@RequestBody LibraryBranch branch) throws JsonProcessingException
//	{
//		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//		headers.add("Accept", accept);
//		try {
//			return  restTemp.exchange(
//					librarianUri+"/LMSLibrarian/branches/{branchId}/name/{name}/address/{address}", HttpMethod.PUT, new HttpEntity<LibraryBranch>(branch, headers),
//					LibraryBranch.class, branch.getBranchId().toString(), branch.getBranchName().toString(), branch.getBranchAddress().toString());
//		}
//		catch(HttpStatusCodeException e) {
//			return new ResponseEntity<LibraryBranch>(e.getStatusCode());
//		}
//	}		
//
//	@PutMapping
//	(value = "/LMSLibrarian/branches/{branchId}/bookId/{bookId}/copies/{copies}",
//	consumes = {"application/xml", "application/json"})
//	public ResponseEntity<?> AddCopies(@RequestHeader("Accept") String accept,
//			@RequestBody BookCopiesBL bookCopy) 
//	{
//		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//		headers.add("Accept", accept);
//
//		try {
//			return restTemp.exchange(librarianUri+"/LMSLibrarian/branches/{branchId}/bookId/{bookId}/copies/{copies}", HttpMethod.PUT, new HttpEntity<BookCopiesBL>(bookCopy, headers),
//					BookCopiesBL.class, bookCopy.getBranchId(), bookCopy.getBookId(), bookCopy.getNoOfCopies());
//		}
//		catch(HttpStatusCodeException e) {
//			return new ResponseEntity<BookCopiesBL>(e.getStatusCode());
//		}
//	}	
//	
//	//Create author
//		@PostMapping(value = "/LMSAdmin/authors/authorName/{authorName}", consumes = {"application/xml", "application/json"})
//		public ResponseEntity<?> insertAuthor(@RequestHeader("Accept") String accept, 
//				@RequestHeader("Content-Type") String contentType, @RequestBody Author author) {
//			
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.add("Content-Type", contentType);
//			headers.add("Accept", accept);
//			
//			try {
//				return restTemp.exchange(adminUri + "/authors/authorName/{authorName}", HttpMethod.POST, 
//						new HttpEntity<Author>(author, headers), Author.class, author.getAuthorName());
//			} catch(HttpStatusCodeException e) {
//				return new ResponseEntity<Author>(e.getStatusCode());
//			}
//		}
//		
//		//Update author
//		@PutMapping(value = "/LMSAdmin/authors/authorId/{authorId}/authorName/{authorName}", consumes = {"application/xml", "application/json"})
//		public ResponseEntity<?> updateAuthor(@RequestHeader("Accept") String accept, 
//				@RequestHeader("Content-Type") String contentType, @RequestBody Author author) {
//			
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.add("Content-Type", contentType);
//			headers.add("Accept", accept);
//			
//			try {
//				return restTemp.exchange(adminUri + "/authors/authorId/{authorId}/authorName/{authorName}", HttpMethod.PUT, 
//						new HttpEntity<Author>(author, headers), Author.class, author.getAuthorId(), author.getAuthorName());
//			} catch(HttpStatusCodeException e) {
//				return new ResponseEntity<Author>(e.getStatusCode());
//			}
//		}
//		
//		//Delete author
//		@DeleteMapping(value = "/LMSAdmin/authors/authorId/{authorId}", consumes = {"application/xml", "application/json"})
//		public ResponseEntity<?> deleteAuthor(@RequestHeader("Accept") String accept, 
//				@RequestHeader("Content-Type") String contentType, @RequestBody Author author) {
//			
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.add("Content-Type", contentType);
//			headers.add("Accept", accept);
//			
//			try {
//				return restTemp.exchange(adminUri + "/authors/authorId/{authorId}", HttpMethod.DELETE, 
//						new HttpEntity<Author>(author, headers), Author.class, author.getAuthorId());
//			} catch(HttpStatusCodeException e) {
//				return new ResponseEntity<Author>(e.getStatusCode());
//			}
//		}
//
//		//View all authors
//		@GetMapping(value = "/LMSAdmin/authors", produces = {"application/xml", "application/json"})
//		public ResponseEntity<Iterable<Author>> getAllAuthors(@RequestHeader("Accept") String accept) {
//			
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.add("Accept", accept);
//			
//			try {
//			return restTemp.exchange(adminUri + "/authors", HttpMethod.GET, new HttpEntity<Object>(headers), 
//					new ParameterizedTypeReference<Iterable<Author>>(){});
//			} catch(HttpStatusCodeException e) {
//				return new ResponseEntity<Iterable<Author>>(e.getStatusCode());
//			}
//		}
//		
//		
//		/*
//		 * Book Orchestrator
//		 */
//		
//		//Create book
//		@PostMapping("/LMSAdmin/books/title/{title}/authId/{authId}/pubId/{pubId}")
//		public ResponseEntity<?> insertBook(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
//				@RequestBody Book book) {
//			
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.add("Content-Type", contentType);
//			headers.add("Accept", accept);
//			
//			try {
//				return restTemp.exchange(adminUri + "/books/title/{title}/authId/{authId}/pubId/{pubId}", HttpMethod.POST, 
//						new HttpEntity<Book>(book, headers), Book.class, book.getTitle(), book.getAuthor().getAuthorId(), 
//						book.getPublisher().getPublisherId());
//			} catch(HttpStatusCodeException e) {
//				return new ResponseEntity<Book>(e.getStatusCode());
//			}
//		}
//		
//		//Update book
//		@PutMapping("/LMSAdmin/books/bookId/{bookId}/title/{title}/authId/{authId}/pubId/{pubId}")
//		public ResponseEntity<?> updateBook(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
//				@RequestBody Book book) {
//			
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.add("Content-Type", contentType);
//			headers.add("Accept", accept);
//			
//			try {
//				return restTemp.exchange(adminUri + "/books/bookId/{bookId}/title/{title}/authId/{authId}/pubId/{pubId}", HttpMethod.PUT, 
//						new HttpEntity<Book>(book, headers), Book.class, book.getBookId(), book.getTitle(), 
//						book.getAuthor().getAuthorId(), book.getPublisher().getPublisherId());
//			} catch(HttpStatusCodeException e) {
//				return new ResponseEntity<Book>(e.getStatusCode());
//			}
//		}
//		
//		//Delete book
//		@DeleteMapping("/LMSAdmin/books/bookId/{bookId}")
//		public ResponseEntity<?> deleteBook(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
//				@RequestBody Book book) {
//			
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.add("Content-Type", contentType);
//			headers.add("Accept", accept);
//			
//			try {
//				return restTemp.exchange(adminUri + "/books/bookId/{bookId}", HttpMethod.DELETE, 
//						new HttpEntity<Object>(book, headers), Book.class, book.getBookId());
//			} catch(HttpStatusCodeException e) {
//				return new ResponseEntity<Book>(e.getStatusCode());
//			}
//		}
//		
//		//View all books
//		@GetMapping(value = "/LMSAdmin/books", produces = {"application/xml", "application/json"})
//		public ResponseEntity<Iterable<Book>> getAllBooks(@RequestHeader("Accept") String accept) {
//			
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.add("Accept", accept);
//			
//			try {
//				return restTemp.exchange(adminUri + "/books", HttpMethod.GET, new HttpEntity<Object>(headers), 
//					new ParameterizedTypeReference<Iterable<Book>>(){});
//			} catch(HttpStatusCodeException e) {
//				return new ResponseEntity<Iterable<Book>>(e.getStatusCode());
//			}
//		}
//		
//		
//		/*
//		 * Borrower Orchestrator
//		 */
//		
//		//Create borrower
//		@PostMapping("/LMSAdmin/borrowers/borrName/{name}/borrAddress/{address}/borrPhone/{phone}")
//		public ResponseEntity<?> insertBorr(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
//				@RequestBody Borrower borrower) {
//			
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.add("Content-Type", contentType);
//			headers.add("Accept", accept);
//			
//			try {
//				return restTemp.exchange(adminUri + "/borrowers/borrName/{name}/borrAddress/{address}/borrPhone/{phone}", 
//					HttpMethod.POST, new HttpEntity<Borrower>(borrower, headers), Borrower.class, borrower.getName(), 
//					borrower.getAddress(), borrower.getPhone());
//			} catch(HttpStatusCodeException e) {
//				return new ResponseEntity<Borrower>(e.getStatusCode());
//			}
//		}
//		
//		//Update borrower
//		@PutMapping("/LMSAdmin/borrowers/cardNo/{cardNo}/borrName/{name}/borrAddress/{address}/borrPhone/{phone}")
//		public ResponseEntity<?> updateBorr(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
//				@RequestBody Borrower borrower) {
//			
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.add("Content-Type", contentType);
//			headers.add("Accept", accept);
//			
//			try {
//				return restTemp.exchange(adminUri + "/borrowers/cardNo/{cardNo}/borrName/{name}/borrAddress/{address}/borrPhone/{phone}", HttpMethod.PUT, 
//						new HttpEntity<Borrower>(borrower, headers), Borrower.class, borrower.getCardNo(), borrower.getName(), 
//						borrower.getAddress(), borrower.getPhone());
//			} catch(HttpStatusCodeException e) {
//				return new ResponseEntity<Borrower>(e.getStatusCode());
//			}
//		}
//		
//		//Delete borrower
//		@DeleteMapping("/LMSAdmin/borrowers/cardNo/{cardNo}")
//		public ResponseEntity<?> deleteBorr(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
//				@RequestBody Borrower borrower) {
//			
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.add("Content-Type", contentType);
//			headers.add("Accept", accept);
//			
//			try {
//				return restTemp.exchange(adminUri + "/borrowers/cardNo/{cardNo}", HttpMethod.DELETE, 
//						new HttpEntity<Borrower>(borrower, headers), Borrower.class, borrower.getCardNo());
//			} catch(HttpStatusCodeException e) {
//				return new ResponseEntity<Borrower>(e.getStatusCode());
//			}
//		}
//		
//		//View all borrower
//		@GetMapping(value = "/LMSAdmin/borrowers", produces = {"application/xml", "application/json"})
//		public ResponseEntity<Iterable<Borrower>> getAllBorrs(@RequestHeader("Accept") String accept) {
//			
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.add("Accept", accept);
//			
//			try {
//				return restTemp.exchange(adminUri + "/borrowers", HttpMethod.GET, new HttpEntity<Object>(headers), 
//					new ParameterizedTypeReference<Iterable<Borrower>>(){});
//			} catch(HttpStatusCodeException e) {
//				return new ResponseEntity<Iterable<Borrower>>(e.getStatusCode());
//			}
//		}
//		
//		
//		/*
//		 * Library Branch Orchestrator
//		 */
//		
//		//Create branch
//		@PostMapping("/LMSAdmin/branches/branchName/{name}/branchAddress/{address}")
//		public ResponseEntity<?> insertBranch(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
//				@RequestBody LibraryBranch branch) {
//			
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.add("Content-Type", contentType);
//			headers.add("Accept", accept);
//			
//			try {
//				return restTemp.exchange(adminUri + "/branches/branchName/{name}/branchAddress/{address}", 
//					HttpMethod.POST, new HttpEntity<LibraryBranch>(branch, headers), LibraryBranch.class, branch.getBranchName(), 
//					branch.getBranchAddress());
//			} catch(HttpStatusCodeException e) {
//				return new ResponseEntity<LibraryBranch>(e.getStatusCode());
//			}
//		}
//		
//		//Update branch
//		@PutMapping("/LMSAdmin/branches/branchId/{branchId}/branchName/{name}/branchAddress/{address}")
//		public ResponseEntity<?> updateBranch(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
//				@RequestBody LibraryBranch branch) {
//			
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.add("Content-Type", contentType);
//			headers.add("Accept", accept);
//			
//			try {
//				return restTemp.exchange(adminUri + "/branches/branchId/{branchId}/branchName/{name}/branchAddress/{address}", HttpMethod.PUT, 
//						new HttpEntity<LibraryBranch>(branch, headers), LibraryBranch.class, branch.getBranchId(), 
//						branch.getBranchName(), branch.getBranchAddress());
//			} catch(HttpStatusCodeException e) {
//				return new ResponseEntity<LibraryBranch>(e.getStatusCode());
//			}
//		}
//		
//		//Delete branch
//		@DeleteMapping("/LMSAdmin/branches/branchId/{branchId}")
//		public ResponseEntity<?> deleteBranch(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
//				@RequestBody LibraryBranch branch) {
//			
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.add("Content-Type", contentType);
//			headers.add("Accept", accept);
//			
//			try {
//				return restTemp.exchange(adminUri + "/branches/branchId/{branchId}", HttpMethod.DELETE, 
//						new HttpEntity<LibraryBranch>(branch, headers), LibraryBranch.class, branch.getBranchId());
//			} catch(HttpStatusCodeException e) {
//				return new ResponseEntity<LibraryBranch>(e.getStatusCode());
//			}
//		}
//		
//		//View all branches
//		@GetMapping(value = "/LMSAdmin/branches", produces = {"application/xml", "application/json"})
//		public ResponseEntity<Iterable<LibraryBranch>> getAvailableBranch(@RequestHeader("Accept") String accept) {
//			
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.add("Accept", accept);
//			
//			try {
//				return restTemp.exchange(adminUri + "/branches", HttpMethod.GET, 
//					new HttpEntity<Object>(headers), new ParameterizedTypeReference<Iterable<LibraryBranch>>(){});
//			} catch(HttpStatusCodeException e) {
//				return new ResponseEntity<Iterable<LibraryBranch>>(e.getStatusCode());
//			}
//		}
//		
//		
//		
//		/*
//		 * Publisher Orchestrator
//		 */
//		
//		//Create pub
//		@PostMapping("/LMSAdmin/publishers/pubName/{publisherName}/pubAddress/{publisherAddress}/pubPhone/{publisherPhone}")
//		public ResponseEntity<?> insertPub(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
//				@RequestBody Publisher pub) {
//			
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.add("Content-Type", contentType);
//			headers.add("Accept", accept);
//			
//			try {
//				return restTemp.exchange(adminUri + "/publishers/pubName/{publisherName}/pubAddress/{publisherAddress}/pubPhone/{publisherPhone}", 
//					HttpMethod.POST, new HttpEntity<Publisher>(pub, headers), Publisher.class, pub.getPublisherName(), 
//					pub.getPublisherAddress(), pub.getPublisherPhone());
//			} catch(HttpStatusCodeException e) {
//				return new ResponseEntity<Publisher>(e.getStatusCode());
//			}
//		}
//		
//		//Update pub
//		@PutMapping("/LMSAdmin/publishers/pubId/{publisherId}/pubName/{publisherName}/pubAddress/{publisherAddress}/pubPhone/{publisherPhone}")
//		public ResponseEntity<?> updatePub(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
//				@RequestBody Publisher pub) {
//			
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.add("Content-Type", contentType);
//			headers.add("Accept", accept);
//			
//			try {
//				return restTemp.exchange(adminUri + "/publishers/pubId/{publisherId}/pubName/{publisherName}/pubAddress/{publisherAddress}/pubPhone/{publisherPhone}", HttpMethod.PUT, 
//						new HttpEntity<Publisher>(pub, headers), Publisher.class, pub.getPublisherId(), pub.getPublisherName(), 
//						pub.getPublisherAddress(), pub.getPublisherName());
//			} catch(HttpStatusCodeException e) {
//				return new ResponseEntity<Publisher>(e.getStatusCode());
//			}
//		}
//		
//		//Delete pub
//		@DeleteMapping(value = "/LMSAdmin/publishers/pubId/{publisherId}")
//		public ResponseEntity<?> deletePub(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
//				@RequestBody Publisher pub) {
//			
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.add("Content-Type", contentType);
//			headers.add("Accept", accept);
//			
//			try {
//				return restTemp.exchange(adminUri + "/publishers/pubId/{publisherId}", HttpMethod.DELETE, 
//						new HttpEntity<Publisher>(pub, headers), Publisher.class, pub.getPublisherId());
//			} catch(HttpStatusCodeException e) {
//				return new ResponseEntity<Publisher>(e.getStatusCode());
//			}
//		}
//		
//		//View publishers
//		@GetMapping(value = "/LMSAdmin/publishers", produces = {"application/xml", "application/json"})
//		public ResponseEntity<Iterable<Publisher>> getAllPubs(@RequestHeader("Accept") String accept) {
//			
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.add("Accept", accept);
//			
//			try {
//			return restTemp.exchange(adminUri + "/publishers", HttpMethod.GET, 
//					new HttpEntity<Object>(headers), new ParameterizedTypeReference<Iterable<Publisher>>(){});
//			} catch(HttpStatusCodeException e) {
//				return new ResponseEntity<Iterable<Publisher>>(e.getStatusCode());
//			}
//		}
//		
//		
//		
//		/*
//		 * Override Orchestrator
//		 */
//		
//		//Override due date
//		@PutMapping("/LMSAdmin/duedate/cardNo/{cardNo}/branchId/{branchId}/bookId/{bookId}/extraDays/{days}")
//		public ResponseEntity<?> overDueDate(@RequestHeader("Accept") String accept, @RequestHeader("Content-Type") String contentType,
//				@RequestBody Override override) {
//			
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.add("Content-Type", contentType);
//			headers.add("Accept", accept);
//			
//			try {
//				return restTemp.exchange(adminUri + "/duedate/cardNo/{cardNo}/branchId/{branchId}/bookId/{bookId}/extraDays/{days}", HttpMethod.PUT, 
//						new HttpEntity<Override>(override, headers), Override.class, override.getCardNo(), override.getBranchId(), 
//						override.getBookId(), override.getDays());
//			} catch(HttpStatusCodeException e) {
//				return new ResponseEntity<Override>(e.getStatusCode());
//			}
//		}
//		
//		//View book loans
//		@GetMapping(value = "/LMSAdmin/loans", produces = {"application/xml", "application/json"})
//		public ResponseEntity<Iterable<BookLoans>> getBookLoans(@RequestHeader("Accept") String accept) {
//			
//			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//			headers.add("Accept", accept);
//			
//			try {
//			return restTemp.exchange(adminUri + "/loans", HttpMethod.GET, 
//					new HttpEntity<Object>(headers), new ParameterizedTypeReference<Iterable<BookLoans>>(){});
//			} catch(HttpStatusCodeException e) {
//				return new ResponseEntity<Iterable<BookLoans>>(e.getStatusCode());
//			}
//		}
	
}
