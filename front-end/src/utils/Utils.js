export function delay(time) {
  return new Promise(resolve => setTimeout(resolve, time));
}

export function getMinimumFractionDigits(price) {
  return price < 1 ? 6 : 2
}
