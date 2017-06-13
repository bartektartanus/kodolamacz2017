package pl.sages.vending;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoinDispenser {

    private int insertedCoinSum = 0;
    private List<Coin> insertedCoin = new ArrayList<>();
    private List<Coin> ownCoin = new ArrayList<>();

    public CoinDispenser(List<Coin> ownCoin) {
        this.ownCoin = ownCoin;
    }

    public void insert(Coin coin) {
        insertedCoin.add(coin);
        insertedCoinSum += coin.getValue();
    }

    // publiczna metoda dostępna dla wszystkich
    // ona wywołuje jedną z dwóch równoważnych implementacji
    public int getTotal(){
        return getTotal1();
    }

    // zbieram monety i na koniec iteruję się po wszystkich
    // aby zsumować łączny wynik
    private int getTotal1(){
        int sum = 0;
        for (Coin coin : insertedCoin) {
            sum += coin.getValue();
            // += to to samo co
            // sum = sum + coin.getValue();
        }
        return sum;
    }

    // podczas wrzucania monet już dodaję ich wartości
    // więc na końcu wystarczy tylko zwrócić wartość
    // JEDNAK NIE NAJLEPSZE PODEJŚCIE
    // bo zbyt wiele razy manipulujmey listą wrzucanych monet
    // przez to w zbyt wielu miejscach trzeba pamiętać o odpowiednim
    // ustawieniu tej zmiennej
    private int getTotal2(){
        return insertedCoinSum;
    }

    public boolean canReturnChange(int change){
        List<Coin> allCoins = new ArrayList<>();
        allCoins.addAll(this.insertedCoin);
        allCoins.addAll(this.ownCoin);
        Collections.sort(allCoins);
        for (Coin coin : allCoins) {
            if(coin.getValue() <= change){
                // a -= b to to samo co a = a-b;
                // a += b to to samo co a = a+b;
                change -= coin.getValue();
                if(change==0){
                    return true;
                }
            }
        }
        return false;
    }

    public List<Coin> returnChange(int change){
        List<Coin> allCoins = new ArrayList<>();
        allCoins.addAll(this.insertedCoin);
        allCoins.addAll(this.ownCoin);
        Collections.sort(allCoins);

        // dodatkowa lista gdzie odkładamy monety do zwrócenia
        List<Coin> changeCoins = new ArrayList<>();
        List<Coin> newOwnCoins = new ArrayList<>();

        for (Coin coin : allCoins) {
            // jeśli moneta powinna być wydana
            if(coin.getValue() <= change){
                change -= coin.getValue();
                // to dodajemy ją do listy
                changeCoins.add(coin);
                // jeśli już nic nie zostało do wydania
                if(change==0){
                    // to przerywamy pętle
                    break;
                }
            }else{
                // jeśli jej nie oddajmy
                // to zostawiamy w maszynie
                newOwnCoins.add(coin);
            }
        }
        this.insertedCoin.clear();
        this.ownCoin = newOwnCoins;
        return changeCoins;
    }

    public List<Coin> returnCoins() {
        List<Coin> coinsToReturn = this.insertedCoin;
        insertedCoin = new ArrayList<>();
        // oddajemy monety, więc suma wrzucony monet musi być 0
        //insertedCoinSum = 0;
        return coinsToReturn;
    }
}
