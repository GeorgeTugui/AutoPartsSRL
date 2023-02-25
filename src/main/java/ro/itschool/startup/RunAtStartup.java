package ro.itschool.startup;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ro.itschool.entity.*;
import ro.itschool.repository.*;
import ro.itschool.service.ShoppingCartService;
import ro.itschool.service.UserService;
import ro.itschool.util.Constants;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RunAtStartup {

    private final UserService userService;

    private final RoleRepository roleRepository;

    private final ProductRepository productRepository;

    private final InteriorRepository interiorRepository;

    private final ExteriorRepository exteriorRepository;

    private final DetailingRepository detailingRepository;

    private final ShoppingCartService shoppingCartService;

    private final UserRepository userRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {

        roleRepository.save(new Role(Constants.ROLE_USER));
        roleRepository.save(new Role(Constants.ROLE_ADMIN));

        saveUser();
        saveAdminUser();
        saveUserToDelete();

        addDetailing();
        addExterior();
        addInterior();

    }

    private void saveAdminUser() {
        MyUser myUser = new MyUser();
        myUser.setUsername("admin");
        myUser.setPassword("admin");
        myUser.setRandomToken("randomToken");
        final Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(Constants.ROLE_USER));
        roles.add(roleRepository.findByName(Constants.ROLE_ADMIN));
        myUser.setRoles(roles);
        myUser.setEnabled(true);
        myUser.setAccountNonExpired(true);
        myUser.setAccountNonLocked(true);
        myUser.setCredentialsNonExpired(true);
        myUser.setEmail("admin1@gmail.com");
        myUser.setFullName("Ion Admin");
        myUser.setPasswordConfirm("admin");
        myUser.setRandomTokenEmail("randomToken");

        userService.saveUser(myUser);
    }

    public void saveUser() {
        MyUser myUser = new MyUser();
        myUser.setUsername("user");
        myUser.setPassword("user");
        myUser.setRandomToken("randomToken");
        final Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(Constants.ROLE_USER));
        myUser.setRoles(roles);
        myUser.setEnabled(true);
        myUser.setAccountNonExpired(true);
        myUser.setAccountNonLocked(true);
        myUser.setCredentialsNonExpired(true);
        myUser.setEmail("user@gmail.com");
        myUser.setFullName("Ion User");
        myUser.setPasswordConfirm("user");
        myUser.setRandomTokenEmail("randomToken");

        userService.saveUser(myUser);
    }

    public void saveUserToDelete() {
        MyUser myUser = new MyUser();
        myUser.setUsername("userToDelete");
        myUser.setPassword("user");
        myUser.setRandomToken("randomToken");
        final Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(Constants.ROLE_USER));
        myUser.setRoles(roles);
        myUser.setEnabled(true);
        myUser.setAccountNonExpired(true);
        myUser.setAccountNonLocked(true);
        myUser.setCredentialsNonExpired(true);
        myUser.setEmail("userDeleteMe@gmail.com");
        myUser.setFullName("Ion DeleteMe User");
        myUser.setPasswordConfirm("user");
        myUser.setRandomTokenEmail("randomToken");

        userService.saveUser(myUser);
    }

    private void addDetailing() {
        String[] names = {"Car wash soap", "Detailing clay", "Wheel cleaner", "Tire shine", "Glass cleaner", "Quick detailer spray", "Leather cleaner", "Leather conditioner", "Upholstery cleaner", "Carpet cleaner", "Bug and tar remover", "Paint sealant", "Wax", "Detailing spray", "Microfiber towels", "Detailing brushes", "Wheel brush", "Foam cannon", "Tire brush", "Trim restorer", "Headlight restoration kit", "Clay bar lubricant", "All-purpose cleaner", "Metal polish", "Engine degreaser", "Car drying towel", "Foam applicator pads", "Interior protectant", "Glass coating", "Interior detailer spray"};
        float[] prices = {14.99f, 24.99f, 19.99f, 12.99f, 9.99f, 16.99f, 19.99f, 22.99f, 17.99f, 15.99f, 11.99f, 29.99f, 34.99f, 14.99f, 19.99f, 12.99f, 14.99f, 39.99f, 17.99f, 12.99f, 39.99f, 14.99f, 12.99f, 24.99f, 9.99f, 14.99f, 22.99f, 49.99f, 29.99f, 11.33f};
        String[] descriptions = {
                "Formulated to clean and remove dirt, grime, and debris from the exterior of your car without damaging the paint or wax.",
                "Designed to remove contaminants from the paint surface of your car, including industrial fallout, overspray, and brake dust, leaving your car's paint smooth and ready for wax.",
                "Powerful formula that dissolves and removes brake dust, grime, and dirt from your car's wheels, restoring their shine.",
                "Helps give your tires a glossy, wet look, while also protecting them from harmful UV rays and preventing cracking and fading.",
                "Quickly and easily cleans the glass surfaces of your car, leaving them crystal clear and streak-free.",
                "Spray-on product that adds a layer of shine and protection to your car's paint, while removing light dust and fingerprints.",
                "Gently cleans the dirt, oil, and grime from your car's leather seats, without damaging the leather.",
                "Conditions and protects your car's leather seats, preventing cracking and fading, and leaving them looking and feeling like new.",
                "Powerful cleaner that removes dirt and stains from your car's upholstery, leaving it fresh and clean.",
                "Specialized cleaner designed to remove tough stains from your car's carpets and floor mats, leaving them looking and smelling like new.",
                "Quickly and easily removes bugs, tar, and other stubborn contaminants from your car's paint.",
                "Long-lasting sealant that protects your car's paint from UV rays, dirt, and other environmental contaminants, while giving it a deep, glossy shine.",
                "Adds a layer of protection to your car's paint, filling in minor scratches and swirl marks, and giving it a high-gloss shine.",
                "Versatile detailing spray that can be used to clean and protect almost any surface on your car, including paint, glass, and plastic.",
                "Soft and absorbent microfiber towels, perfect for detailing and cleaning your car, without scratching or damaging the surface.",
                "Set of specialized brushes designed to clean hard-to-reach areas in your car, including vents, cup holders, and other tight spaces.",
                "Long-handled brush designed to clean your car's wheels, removing brake dust and grime from even the tightest spaces.",
                "Attaches to your pressure washer, producing thick foam to help clean your car's exterior.",
                "Designed to clean your car's tires, removing dirt and grime from even the tightest spaces.",
                "Restores faded and discolored plastic and rubber trim on your car, leaving it looking like new.",
                "Complete kit for restoring the clarity of your car's headlights, improving visibility and safety while driving.",
                "Lubricant that helps the detailing clay glide smoothly over your car's paint, removing contaminants and leaving the surface smooth.",
                "Powerful all-purpose cleaner that can be used on a variety of surfaces in your car, including carpets, upholstery, and plastic.",
                "Polish that cleans and restores the shine of metal surfaces in your car, including chrome, aluminum, and stainless steel.",
                "Powerful degreaser that quickly dissolves and removes oil and grease from your car's engine compartment and other areas.",
                "Soft and absorbent towel designed to quickly and easily dry your car after washing, preventing water spots and streaks.",
                "Foam applicator pads for applying wax, sealant, and other detailing products to your car's exterior.",
                "Protects your car's interior surfaces from UV rays, dust, and other environmental contaminants.",
                "Protects your windshield and helps disperse the water.",
                "Quickly clean and protect the interior of your car with this versatile spray, designed to remove dirt, dust, and fingerprints from almost any surface, including plastic, vinyl, and leather"};

        for (int i = 0; i < 30; i++) {
            Detailing detailing = new Detailing();
            detailing.setName(names[i]);
            detailing.setPrice(prices[i]);
            detailing.setDetailingdescription(descriptions[i]);
            detailing.setDeleted(Boolean.FALSE);
            productRepository.save(detailing);
        }
    }

    private void addExterior() {
        List<String> names = Arrays.asList("Side Mirror", "Hood Scoop", "Rear Spoiler", "Roof Rack", "Front Grille", "Door Handle", "Fog Light", "Running Board", "Chrome Trim", "Exhaust Tip", "Wind Deflector", "Grille Guard", "Moon Roof", "Rear Bumper", "Wheel Cover", "Front Bumper", "Body Kit", "Fender Flare", "Headlight Cover", "Rear Wing", "License Plate Frame", "Trunk Organizer", "Car Cover", "Cargo Net", "Seat Covers", "Floor Mats", "Sunshade", "Steering Wheel Cover", "Air Freshener", "Phone Holder");
        List<Float> prices = Arrays.asList(50.0f, 60.0f, 55.0f, 80.0f, 79.0f, 100.0f, 97.0f, 88.0f, 130.0f, 112.0f, 150.0f, 133.0f, 170.0f, 221.0f, 190.0f, 176.0f, 210.0f, 211.0f, 230.0f, 240.0f, 25.0f, 35.0f, 120.0f, 20.0f, 90.0f, 50.0f, 15.0f, 25.0f, 5.0f, 20.0f);
        List<String> descriptions = Arrays.asList("Enhances the appearance of the car", "Improves aerodynamics", "Adds style and sportiness", "Provides extra storage", "Enhances the front appearance", "Easy and convenient to use", "Helps improve visibility", "Protects the side of the car", "Adds a touch of elegance", "Improves the sound", "Reduces wind noise and turbulence", "Protects the front of the car", "Brings in natural light", "Protects the rear of the car", "Adds a touch of sophistication", "Protects the front of the car", "Improves the overall appearance", "Protects the headlights", "Adds downforce for improved performance", "Protects the side of the car", "Frames and protects license plates", "Keeps the trunk organized", "Protects the car from weather and debris", "Helps secure loose items in the trunk", "Protects seats from wear and tear", "Covers the car floor for easy cleaning", "Blocks sunlight and heat", "Adds grip and comfort to steering wheel", "Keeps the car smelling fresh", "Holds phone securely while driving");


        for (int i = 0; i < 30; i++) {
            Exterior exterior = new Exterior();
            exterior.setName(names.get(i));
            exterior.setPrice(prices.get(i));
            exterior.setExteriorpartdescription(descriptions.get(i));
            exterior.setDeleted(Boolean.FALSE);
            productRepository.save(exterior);
        }
    }

    private void addInterior() {
        List<String> names = Arrays.asList(
                "Leather Steering Wheel Cover", "Floor Mats", "Seat Covers", "Sunshade", "Carpet Protector",
                "Steering Wheel Lock", "Trunk Organizer", "Interior Cleaner", "Armrest Cover", "Sun Visor Extender",
                "Air Freshener", "Blanket", "Rear Seat Entertainment", "Charging Mat", "Door Sill Protectors",
                "Seat Massager", "Gear Shift Knob Cover", "Trunk Mat", "Car Trash Can", "Steering Wheel Cover",
                "Rearview Camera", "Blind Spot Mirrors", "GPS Navigation System", "Car Phone Mount", "Dash Cam",
                "Bluetooth Adapter", "Backup Sensors", "Car Cover", "Hitch Mount Bike Rack", "Tire Inflator");
        List<Float> prices = Arrays.asList(
                15.99f, 20.99f, 35.99f, 10.99f, 25.99f,
                20.99f, 15.99f, 5.99f, 10.99f, 15.99f,
                2.99f, 20.99f, 50.99f, 20.99f, 10.99f,
                45.99f, 10.99f, 20.99f, 10.99f, 5.99f,
                99.99f, 25.99f, 199.99f, 14.99f, 79.99f,
                29.99f, 79.99f, 99.99f, 149.99f, 29.99f);
        List<String> descriptions = Arrays.asList(
                "Made of premium leather", "Durable and water resistant", "Protects original seat from wear and tear", "Blocks harmful UV rays", "Easy to clean",
                "Ensures car security", "Maximizes trunk storage", "Eliminates unwanted odors", "Comfortable armrest protection", "Reduces sun glare",
                "Long-lasting fragrance", "Cozy and warm", "Entertainment on the go", "Convenient charging solution", "Protects door sill from scratches",
                "Relaxes and soothes", "Upgrade your driving experience", "Protects trunk from dirt and spills", "Comfortable grip and stylish look", "Looks nice!",
                "Provides a clear view of what's behind your car", "Helps you see vehicles in your blind spot", "Guides you to your destination with turn-by-turn directions", "Holds your phone securely while driving", "Records video of your drives for security and insurance purposes",
                "Allows you to connect your phone to your car's audio system", "Alerts you to objects behind your car while backing up", "Protects your car from weather, dust, and scratches", "Allows you to transport bikes safely and easily", "Inflates your tires quickly and easily");

        for (int i = 0; i < 30; i++) {
            Interior interior = new Interior();
            interior.setName(names.get(i));
            interior.setPrice(prices.get(i));
            interior.setInteriorpartdescription(descriptions.get(i));
            interior.setDeleted(Boolean.FALSE);
            interiorRepository.save(interior);
        }
    }
}


