use std::io;


//Recursive approach 
// fn fibonacci(n: u32) -> u32 {
//     match n {
//        0 => 0,
//        1 => 1,
//        2 => 1,
//        _ => fibonacci(n - 1) + fibonacci(n - 2),


//     }

// } 


//Memoized approach 
fn fibonacci(n: u32) -> u32 {

   let mut fib_seq = Vec::new();
   fib_seq.push(0);
   fib_seq.push(1);

   for i in 2..n {
      let fib_i = fib_seq[(i-1) as usize] + fib_seq[(i-2) as usize];
      fib_seq.push(fib_i);
   }
   *fib_seq.last().unwrap()
}

fn main() {
   
   println!("Write in your desired number:");

   let mut n = String::new();
   io::stdin().read_line(&mut n).expect("Failed to read in data");
   let n: u32 = n.trim().parse().expect("Wrong data type!");

   let fib_seq = fibonacci(n);

   println!("The required number in the Fibonacci sequence is: {}", fib_seq);
}
