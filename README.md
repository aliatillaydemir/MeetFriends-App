# MeetFriends-App
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

Hiç tanımadığınız insanlarla tanışmanızı sağlayacak, çevre-insan uyumunu göz önüne alarak tasarlanmış sosyal mobil :iphone: proje.


<div>

## Platform/Dil:
<code><img height="50" src="https://user-images.githubusercontent.com/25181517/117201156-9a724800-adec-11eb-9a9d-3cd0f67da4bc.png" alt="HTTP" title="HTTP" /></code>
<code><img height="50" src="https://user-images.githubusercontent.com/25181517/117269608-b7dcfb80-ae58-11eb-8e66-6cc8753553f0.png" alt="HTTP" title="HTTP" /></code>

## Projede Kullanılan Teknoloji ve Kütüphaneler

### Veri Tabanı hizmetleri:

- Lokal için: SQLite
- Content Provider(İçerik Sağlayıcılar).

- Bulut için: Firebase

- Firebase ürünleri:

1. firebase Authentication
2. firebase Realtime Database
3. firebase Firestore Database
4. firebase Storage
5. firebase messaging -bildirim için(onesignal).

### Diğer teknolojiler:
	
- Kullanıcıların haberleşmesinde kullanılacak push notificationlar için oneSignal.

- ~~Login işlemi için provider olarak facebook sdk kullanılmıştır, firebase Authentication ile bağlantı sağlanmıştır.~~

- Api haberleşmesi için Volley
- Openweathermap api hava durumu bilgileri için. Volley ile birlikte.
	
- Google maps api harita bilgileri için.

- Picasso resim/fotoğraf download ve upload'ı için firebase ürünleriyle birlikte.

- View binding kullanılmıştır.

- google play service(google admob).
1. Banner
2. Interstitial

- RecylerViewlar. Not alma kısmında listView.

- Görsel tasarımlar için Adobe xd ve Figma.	

[Haritada kullanılan markerlar için tıklayınız.](https://icons8.com)
	
</div>

## Uygulama Icon'u

<img width="159" alt="Screenshot_36" src="https://user-images.githubusercontent.com/43906043/175795191-63ff4fea-3f67-4ddd-84b8-d9f179ff94a4.png" align="left" width="200px"> Uygulama icon'u tasarlanırken sadelik amaçlanmıştır. Kare içindeki "MF" uygulama ismi olan MeetFriends'ten gelmektedir. Yeşil ve beyaz renkler uygulamanın sosyal çevre ile ilgili olmasından dolayı; projeye doğallık ve sadelik hissiyatı vermeyi amaçlamaktadır. Bu bakımdan uygulamanın içerik ve tasarımının da
bu düşünceye uygun olduğu görülebilir. 

<br clear/>
<br/>
<br/>

## Giriş-Kayıt İşlemleri

<img width="250" alt="Screenshot_1" src="https://user-images.githubusercontent.com/43906043/175795103-2dff1649-8287-4e3a-af2f-a70fa0fb3a13.png"> <img width="247" alt="Screenshot_2" src="https://user-images.githubusercontent.com/43906043/175795104-3dede3d2-6186-4522-9922-58bc480b80c9.png"> <img width="254" alt="Screenshot_3" src="https://user-images.githubusercontent.com/43906043/175795105-f9ed474c-ab81-4798-9936-6c52d655e416.png">

Kulllanıcı uygulamaya daha önceden giriş yapmadıysa veya çıkış yapma seçeneğini kullanırsa karşısına login ekranı çıkar. Bu kısım 3 parçadan oluşur:
- Login ekranı,
- Şifremi unuttum ekranı,
- Yeni bir hesap oluşturma ekranı.

Bu kısımlarda eşleşme, format kontrolü gibi işlemler yapılarak kullanıcının uygulamaya giriş yapması sağlanır. Kullanıcı verileri firebase'in authentication section'ında
tutulur ve sorgulanır.

## Uygulama Ana Ekranı

<img width="256" alt="Screenshot_4" src="https://user-images.githubusercontent.com/43906043/175795106-c5d5ef90-db17-49ee-b902-d8f4f1c49b30.png"> 

Uygulamanın ana ekranı gridler üzerine kurulmuş bir tasarımla oluşturulmuştur. Tercih ve farklılık sebeplerinden dolayı sadece görsellerle sayfalar arası geçiş için
tasarım uygulanmıştır.

Bu sayfada kullanıcının seçebileceği 6 temel sayfa(aktivite/fragment topluluğu) bulunur.
1. Konum(Harita) sayfası, 
2. Etkinlik sayfası,
3. Profil sayfası,
4. Hava durumu sayfası,
5. Info(Bilgiler) sayfası,
6. Ayarlar sayfası.
- Bu sayfalar da kendi içlerinde çeşitli sayfalara ayrılmaktadırlar. 

<img width="255" alt="Screenshot_5" src="https://user-images.githubusercontent.com/43906043/175795107-14853812-aff6-4dbb-b52b-e0abfab892ce.png">

Uygulama ana ekranında bulunan menü ile Şifre değiştirme, Email güncelleme, Hesap silme ve Çıkış yapma gibi işlemler yapılabilmektedir.

<img width="255" alt="Screenshot_6" src="https://user-images.githubusercontent.com/43906043/175795108-973aef4e-4030-481c-9ac1-527ff0fdf742.png"> <img width="257" alt="Screenshot_7" src="https://user-images.githubusercontent.com/43906043/175795110-4b5f3edd-c15a-4780-a2cc-22a2cf40b193.png"> <img width="255" alt="Screenshot_8" src="https://user-images.githubusercontent.com/43906043/175795111-21b41628-5dd0-4027-a232-dcbe45708d96.png">

<br/>
<br/>

## 1. Konum(Harita) sayfası

<img width="251" alt="Screenshot_9" src="https://user-images.githubusercontent.com/43906043/175795112-da5bfc4e-3dc4-4b8f-901d-a8c7cf99d9b5.png"> <img width="253" alt="Screenshot_10" src="https://user-images.githubusercontent.com/43906043/175795113-d34740a8-4c08-4d1d-9781-86f620137f3e.png"> <img width="257" alt="Screenshot_11" src="https://user-images.githubusercontent.com/43906043/175795114-f776b406-c57d-43bf-83bd-336977bd9d42.png"> 

Konum sayfasında kullancının 2 seçeneği olur. Bunlardan bir tanesi "buluşma lokasyonları" butonuna, diğer ise "buluşma başlat" butonuna tıklamaktır. 

### a. Buluşma Lokasyonları butonu
Sayfa ilk açıldığında sadece kullanıcının lokasyonu kırmızı bir markerla gözükmekteyken, buluşma lokasyonu butonuyla birlikte açılmış diğer buluşma etkinlikleri görünmektedir.
Kullanıcı bu işaretçilerden birini seçerek bulunduğu konumdan başlatılan etkinlik konumuna nasıl gidileceğini öğrenebilir(google maps yardımıyla).

### b. Buluşma Başlat butonu
<img width="251" alt="Screenshot_12" src="https://user-images.githubusercontent.com/43906043/175795115-302e76f9-763a-426a-a516-0c12117f564b.png">

Buluşma Başlat butonu kullanıcıyı ayrı bir sayfaya atar. Bu sayfada kullanıcı fotoğraf seçme ekranına tıklayarak buluşmayı başlatacağı alanın, çevrenin, fotoğrafını
seçer, tarihi belirler ve açıklama ekler. Ardından etkinliği başlat butonuna tıklayarak etkinliği başlatır. Kullanıcı örneğin açıklama eklemezse hata mesajı döner ve
veriler bulut veri tabanına ulaşmaz. Buluşma etkinliğini başlatan kullanıcı tarih belirtmezse bulunduğu günün tarihi otomatik eklenir.

<img width="257" alt="Screenshot_13" src="https://user-images.githubusercontent.com/43906043/175795117-c8237628-1c51-41e8-a4bd-d6b680bf4f8a.png"> <img width="258" alt="Screenshot_14" src="https://user-images.githubusercontent.com/43906043/175795118-f7fda3e7-3af2-48d7-975a-aa4c990c8192.png">

- İlgili işlemler için google maps(bu kısımda etkinlik sayfasına dönmesi için konum bilgileri alınır), firestore, firabase store gibi hizmetler kullanılmıştır.

<br/>
<br/>

## 2. Etkinlik sayfası

Kendi içinde 2'ye, totalde 4'e ayrılmıştır.

a. Etkinlik Ana sayfası, <br/>
a1. Chat Ekranı, <br/>
b. Detaylar Fragment'i, <br/>
b1. Not ekleme sayfası. 

### a. Etkinlik Ana sayfası

<img width="256" alt="Screenshot_15" src="https://user-images.githubusercontent.com/43906043/175795119-b8faf2ce-64e4-476b-b6bf-5a5c838ee54f.png"> <img width="257" alt="Screenshot_37" src="https://user-images.githubusercontent.com/43906043/175814207-61a770f6-252d-4097-8104-6d9b136c7c19.png"> <img width="259" alt="Screenshot_38" src="https://user-images.githubusercontent.com/43906043/175814214-970f4281-3f8d-40d4-a4cc-a4da3e95b142.png">

Etkinlik sayfasında, buluşma etkinlikleri dikdörtgen fotoğraflar şeklinde paylaşılma tarihlerine göre sıralanır. Kullanıcı üzerine tıkladığında background'u şeffaf 
olacak şekilde bir pop-up sayfası açılır. Bu kısımda buluşmayı başlatan kurucu bilgileri, tarih, adres ve açıklama yer alır. Ayrıca kullanıcının Etkinliği bitir ile
kalıcı bir marker ekleyebilir ya da etkinliği silebilir. Eğer etkinliği kullanıcının kendisi oluşturmadıysa bu butonlar gözükmemektedir. Etkinlikler RecyclerView 
kullanılarak listelenmiştir.

### a1. Chat Ekranı

Ekranın sağ alt kısmında bulunan buton yardımıyla mesajlaşma sayfasına yönlendirilir. Burada firebase real-time database ve bildirimler için oneSignal kullanılmıştır.
Kullanıcılar bu kısımda birbirleriyle mesajlaşabilir ama henüz ham bir özelliktir. Herkes girip bir şeyler yazabilir durumdadır. Kişi-Kişi eşleşmesi yapılarak, yani
kullancı id'leri eşleştirilerek özel sohbet oluşturulabilir.

<img width="257" alt="Screenshot_41" src="https://user-images.githubusercontent.com/43906043/175815596-9268871a-f316-4ec6-a9a4-acf2ed7c5c16.png"> <img width="257" alt="Screenshot_42" src="https://user-images.githubusercontent.com/43906043/175815597-b5e5100d-6283-4bf5-b382-e3479acb7561.png">


### b. Detaylar Fragment

Tab layout sayesinde etkinlik ana sayfasından detaylar sayfasına ekran kaydıralarak geçiş yapılabilir. Bu kısım kullanıcıların not alması ve tarih üzerinden hesaplama
yapabilmesi için oluşturulmuştur. Amaç kullanıcının projeye dair her ihtiyacını uygulamadan çıkmadan görebilmesi ve vakit geçirebilmesidir. 

<img width="258" alt="Screenshot_16" src="https://user-images.githubusercontent.com/43906043/175795121-6f301443-0776-410d-8bde-96ca9d23bd3b.png"> <img width="255" alt="Screenshot_2" src="https://user-images.githubusercontent.com/43906043/176209912-c52d8c72-9ecb-4257-9c08-e49aa93caeac.png">

Ekranın sol altında bulunan artı şeklindeki float buton ile not ekleme sayfasına geçiş yapılabilir.


### b1. Not ekleme sayfası

Kullanıcı bu sayfa sayesinde not ekleyebilir. Content Provider ile lokal bir veri tabanı olan sqlite verileri tutmaktadır. İlk not eklendiğinde sadece kaydetme butonu 
gözükmektedir.

<img width="256" alt="Screenshot_17" src="https://user-images.githubusercontent.com/43906043/175795122-ca40f9bf-9637-460f-9e9d-234b6225e060.png"> <img width="256" alt="Screenshot_18" src="https://user-images.githubusercontent.com/43906043/175795124-77fa2e1e-6225-455f-93f6-f43806308830.png">

Eklenmiş notlar üzerine basarak da güncelleme ve silme işlemi yapılabilir.

<img width="248" alt="Screenshot_40" src="https://user-images.githubusercontent.com/43906043/175815142-496d814a-28b1-4617-adc2-ec572ce0cd5d.png">

<br/>
<br/>

## 3. Profil sayfası

Profil sayfası kullanıcıların profil bilgilerin görebileceği ve bu bilgileri değiştirebileceği 2 kısımdan oluşur. Ana sayfa açıldığında isim, yaş, konum, profil fotoğrafı
gibi kayıtlı veriler görünür. Eğer bu bilgileri değiştirmek istenir ya da hiç kaydedilmediği için oluşturmak istenilirse düzenle butonuna tıklayarak profil düzenleme 
ekranına ulaşabilir.

<img width="258" alt="Screenshot_21" src="https://user-images.githubusercontent.com/43906043/175795128-2dc907e6-d535-4c10-9833-00d76f725951.png"> <img width="255" alt="Screenshot_22" src="https://user-images.githubusercontent.com/43906043/175795130-94fdb571-f8f4-46d2-bc83-890861873d26.png"> <img width="258" alt="Screenshot_23" src="https://user-images.githubusercontent.com/43906043/175795131-f84e9d1f-9575-4521-a9b8-a592f21254d7.png"> 
- bu kısımda firebase'in bazı hizmetleri ve google banner(kullanıcıyı rahatsız etmemek için ekranın altında bulunan küçük reklam kutusu) kullanılmıştır.

Not: Ana sayfada profil fotoğrafına tıklanırsa medya açılır. Buradan seçim yapıldığında ekrana gelen resmin kaydolması için yanda yer alan kayıt sembolüne basılmalıdır,
aksi takdirde fotoğraf veri tabanına kaydedilmeyeceğinden sonraki giriş çıkışlarda gösterilmeyecektir. Eğer fotoğraf seçilmeden bu sembole tıklanırsa işlem gerçekleşmez
ve uygulama hata verir.

<br/>
<br/>

## 4. Hava durumu sayfası

Uygulama sosyal bir projenin sonucu olduğun dolayı kullanıcının çıkmadan olabildiğince ilgili işlemleri yapabilmesi gerektiği amaç olarak belirtilmişti. Bu yüzden
kullanıcı istediği konumun hava durumu bilgilerini alabilmek için hava durumu sayfasını kullanabilir. yerleşim yerinin ismini yazmak yeterlidir.

<img width="258" alt="Screenshot_24" src="https://user-images.githubusercontent.com/43906043/175795132-b20d858f-315b-4053-b415-031a03f43730.png"> <img width="258" alt="Screenshot_25" src="https://user-images.githubusercontent.com/43906043/175795133-5cf4216f-0930-483b-bf69-3175e2560eb3.png">

- Bu aktivite için OpenWeatherMap api ve haberleşme için volley ağ kütüphanesi kullanılmıştır.

<br/>
<br/>

## 5. Info(Bilgiler) sayfası

Tablayout, fargment'lar ve adaptör sınıflarından oluşur. 3 kısım vardır, kullanıcıya bilgi vermek amacıyla yazılmıştır.

<img width="259" alt="Screenshot_26" src="https://user-images.githubusercontent.com/43906043/175795134-d920a308-4743-4c06-9bdb-ab969e33ab19.png"> <img width="257" alt="Screenshot_27" src="https://user-images.githubusercontent.com/43906043/175795136-e56df563-d99d-49cb-a91f-f60cca429cc5.png"> <img width="258" alt="Screenshot_28" src="https://user-images.githubusercontent.com/43906043/175795138-90864528-99e6-42fb-aa30-8dab3ffd9f63.png">

<br/>
<br/>

### 6. Ayarlar sayfası

<img width="255" alt="Screenshot_29" src="https://user-images.githubusercontent.com/43906043/175795139-84b06a0a-0b4c-492c-8688-fc63b446f52f.png"> <img width="257" alt="Screenshot_30" src="https://user-images.githubusercontent.com/43906043/175795140-d529c0f4-50b7-4d44-bc18-77439a3b1e02.png"> <img width="259" alt="Screenshot_31" src="https://user-images.githubusercontent.com/43906043/175795141-ea58b5bc-ea10-4d40-a5a7-62d353977664.png">

Ayarlar sayfasına giriş yapıldığında kullanıcıyı interstitial reklamı karşılar. Reklam geçildiğinde gelen ekran prototiptir. Burada çalışır vaziyette 2 önemli özellik
bulunur: <br/>
a. Email Onayı, <br/>
b. Dark Mod.

### a. Email Onayı

Uygulama Kullanıcının mailini onaylamasını zorunlu tutmaz ama bu onay gerçekleşmediği sürece ayarlar sayfasında onay yazısı ve butonu görülecektir. Kullanıcı mail'i 
üzerinden onay işlemini gerçekleştirdiği anda bu yazı ve buton sonsuza kadar ayarlar sayfasından kalkar.

### b. Dark Mod

Gece modu(karanlık mod) sharedPreference ile cihazda seçenek bilgisinin tutulmasıyla uygulamada yer alır. Kullanıcı uygulamayı kapatıp açsa bile bilgiler saklanır.

<img width="260" alt="Screenshot_32" src="https://user-images.githubusercontent.com/43906043/175795142-bcd9d454-3655-4b97-83ff-4531ed7e7264.png"> <img width="257" alt="Screenshot_33" src="https://user-images.githubusercontent.com/43906043/175795143-3ca78b4f-e06c-4337-a1f0-860fa6a65be8.png"> <img width="256" alt="Screenshot_34" src="https://user-images.githubusercontent.com/43906043/175795144-c80b4a06-f3b5-4ed5-86cf-03c7526b1df5.png">


### NOT: 
Kullanıma hazır olması için aşağıdaki sınıflarda/dosyalarda bulunan key'lerin girilmesi gerekmektedir.<br/>
- ChatActivity ->  ONESIGNAL_APP_ID<br/>
- WeatherActivity -> appid<br/>
- manifest -> android.gms.ads, google.android.geo keys

